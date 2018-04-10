package org.pdfsam.pdf;


import java.util.concurrent.atomic.AtomicInteger;

public class PdfDocumentDescriptorReference {
	private AtomicInteger references = new AtomicInteger(1);

	/**
	* @return  true if this descriptor has references, this can be false if the user deletes it from the UI and it tells to any service performing or about to perform some action on the descriptor that it should be ignored since not relevant anymore.
	*/
	public boolean hasReferences() {
		return references.get() > 0;
	}

	/**
	* @return  true if the descriptor has become invalid because of the release
	*/
	public boolean release() {
		return this.references.decrementAndGet() <= 0;
	}

	public void releaseAll() {
		this.references.set(0);
	}

	/**
	* Increment the number of reference
	*/
	public PdfDocumentDescriptor retain(PdfDocumentDescriptor pdfDocumentDescriptor) {
		this.references.incrementAndGet();
		return pdfDocumentDescriptor;
	}
}