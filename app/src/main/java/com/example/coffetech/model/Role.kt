package com.example.coffetech.model

// Definir la estructura de Permiso
data class Permission(
    val permission_id: Int,
    val name: String,
    val description: String
)

// Actualización de Role para incluir la lista de permisos
data class Role(
    val role_id: Int,
    val name: String,
    val permissions: List<Permission> // Lista de permisos asociados al rol
)
