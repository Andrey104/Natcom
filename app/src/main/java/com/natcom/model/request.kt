package com.natcom.model

data class CloseRequest(val contract: Boolean, val mount: Boolean, val comment: String, val date: String)
data class ShiftRequest(val date: String, val comment: String)
data class DenyRequest(val comment: String)
