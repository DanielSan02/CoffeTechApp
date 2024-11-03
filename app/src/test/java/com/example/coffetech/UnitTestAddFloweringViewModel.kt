package com.example.coffetech

import com.example.coffetech.viewmodel.flowering.AddFloweringViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@ExperimentalCoroutinesApi
class AddFloweringViewModelTest {

    private lateinit var viewModel: AddFloweringViewModel

    @Before
    fun setup() {
        viewModel = AddFloweringViewModel()
    }

    @Test
    fun `validateInputs should return false when flowering_date is blank`() = runTest {
        viewModel.onFloweringDateChange("")
        viewModel.onFloweringNameChange("Principal")
        val result = viewModel.validateInputs()
        assertFalse(result)
        assertEquals("La fecha de floración no puede estar vacía.", viewModel.errorMessage.first())
    }

       @Test
    fun `validateInputs should return false when flowering_date is after current date`() = runTest {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val futureDate = Calendar.getInstance().apply { add(Calendar.DATE, 1) }.time
        viewModel.onFloweringDateChange(dateFormat.format(futureDate))
        viewModel.onFloweringNameChange("Principal")
        val result = viewModel.validateInputs()
        assertFalse(result)
        assertEquals("La fecha de floración no puede ser posterior a la fecha actual.", viewModel.errorMessage.first())
    }

    @Test
    fun `validateInputs should return false when harvest_date is before flowering_date`() = runTest {
        viewModel.onFloweringDateChange("2023-01-01")
        viewModel.onHarvestDateChange("2022-12-31")
        viewModel.onFloweringNameChange("Principal")
        val result = viewModel.validateInputs()
        assertFalse(result)
        assertEquals("La fecha de cosecha no puede ser antes de la fecha de floración.", viewModel.errorMessage.first())
    }

        @Test
    fun `validateInputs should return false when harvest_date is less than 24 weeks after flowering_date`() = runTest {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val floweringDate = "2023-01-01"
        val invalidHarvestDate = Calendar.getInstance().apply {
            time = dateFormat.parse(floweringDate)!!
            add(Calendar.WEEK_OF_YEAR, 20) // Menos de 24 semanas después
        }.time

        viewModel.onFloweringDateChange(floweringDate)
        viewModel.onHarvestDateChange(dateFormat.format(invalidHarvestDate))
        viewModel.onFloweringNameChange("Principal")
        val result = viewModel.validateInputs()
        assertFalse(result)
        assertEquals("La fecha de cosecha debe ser al menos 24 semanas después de la fecha de floración.", viewModel.errorMessage.first())
    }

    @Test
    fun `validateInputs should return true when all inputs are valid`() = runTest {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val floweringDate = "2023-01-01"
        val validHarvestDate = Calendar.getInstance().apply {
            time = dateFormat.parse(floweringDate)!!
            add(Calendar.WEEK_OF_YEAR, 24) // Exactamente 24 semanas después
        }.time

        viewModel.onFloweringDateChange(floweringDate)
        viewModel.onHarvestDateChange(dateFormat.format(validHarvestDate))
        viewModel.onFloweringNameChange("Principal")
        val result = viewModel.validateInputs()
        assertTrue(result)
        assertEquals("", viewModel.errorMessage.first())
    }
}

