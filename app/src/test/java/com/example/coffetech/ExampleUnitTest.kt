import com.example.coffetech.viewmodel.farm.CreateFarmViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.flow.first
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method

@ExperimentalCoroutinesApi
class CreateFarmViewModelTest {

    private lateinit var viewModel: CreateFarmViewModel

    @Before
    fun setup() {
        viewModel = CreateFarmViewModel()
    }
    private fun invokeValidateInputs(): Boolean { val method: Method = CreateFarmViewModel::class.java.getDeclaredMethod("validateInputs")
        method.isAccessible = true // Desbloquea el acceso al método privado
         return method.invoke(viewModel) as Boolean }
    @Test
    fun `validateInputs should return false when farmName is blank`() = runBlockingTest {
        viewModel.onFarmNameChange("")
        val result = invokeValidateInputs()
        assertFalse(result)
        assertEquals("El nombre de la finca no puede estar vacío.", viewModel.errorMessage.first())
    }

    @Test
    fun `validateInputs should return false when farmArea is invalid`() = runBlockingTest {
        viewModel.onFarmNameChange("Finca 1")
        viewModel.onFarmAreaChange("0")
        val result = invokeValidateInputs()
        assertFalse(result)
        assertEquals("El área debe ser un número mayor a 0 y menor a 10000.", viewModel.errorMessage.first())
    }

    @Test
    fun `validateInputs should return false when selectedUnit is invalid`() = runBlockingTest {
        viewModel.onFarmNameChange("Finca 1")
        viewModel.onFarmAreaChange("100")
        viewModel.onUnitChange("Seleccione unidad de medida")
        val result = invokeValidateInputs()
        assertFalse(result)
        assertEquals("Debe seleccionar una opción válida para la unidad de medida.", viewModel.errorMessage.first())
    }

    @Test
    fun `validateInputs should return true when all inputs are valid`() = runBlockingTest {
        viewModel.onFarmNameChange("Finca 1")
        viewModel.onFarmAreaChange("100")
        viewModel.onUnitChange("Hectáreas")
        val result = invokeValidateInputs()
        assertTrue(result)
        assertEquals("", viewModel.errorMessage.first())
    }
}


