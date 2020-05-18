package com.example.magesticreader.interactors

import com.example.magesticreader.data.DocumentRepository
import com.example.magesticreader.domain.Document

class RemoveDocument(private val repository: DocumentRepository) {
    suspend operator fun invoke(document: Document) = repository.removeDocument(document)
}