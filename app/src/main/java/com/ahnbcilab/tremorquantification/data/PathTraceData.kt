package com.ahnbcilab.tremorquantification.data

data class PathTraceData(val x: Float, val y: Float, val t: Float) {
    val joinToString = { del: String -> "${this.x}$del${this.y}$del${this.t}"}
}