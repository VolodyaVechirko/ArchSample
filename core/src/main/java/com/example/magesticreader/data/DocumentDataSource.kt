package com.example.magesticreader.data

import com.example.magesticreader.domain.Document

interface DocumentDataSource {

    suspend fun add(document: Document)

    suspend fun readAll(): List<Document>

    suspend fun remove(document: Document)
}