package ir.vsr;

import java.io.*;
import java.util.*;
import java.lang.*;

public class DocumentIterator {

  public static final short TYPE_TEXT = 0;
  public static final short TYPE_HTML = 1;
  protected File[] files = null;
  protected int position = 0;
  protected short docType = TYPE_TEXT;
  protected boolean stem = false;
  
  public DocumentIterator(File dirFile, short docType, boolean stem, FilenameFilter filter) {
    // Get the files in this directory
    if (filter != null)
      files = dirFile.listFiles(filter);
    else
      files = dirFile.listFiles();
    position = 0;
    this.docType = docType;
    this.stem = stem;
  }
  public DocumentIterator(File dirFile, short docType, boolean stem) {
    this(dirFile, docType, stem, null);
  }

  public DocumentIterator(File dirFile) {
    this(dirFile, TYPE_TEXT, false);
  }

  public FileDocument nextDocument() {
    if (position >= files.length)
      return null;
    FileDocument doc = null;
    switch (docType) {
      case TYPE_TEXT:
        doc = new TextFileDocument(files[position], stem);
        break;
      case TYPE_HTML:
        doc = new HTMLFileDocument(files[position], stem);
        break;
    }
    position++;
    return doc;
  }

  public boolean hasMoreDocuments() {
    if (files != null && position < files.length)
      return true;
    else
      return false;
  }

  public static void main(String[] args) {
    String dirName = args[0];
    DocumentIterator docIter = new DocumentIterator(new File(dirName));
    while (docIter.hasMoreDocuments()) {
      FileDocument doc = docIter.nextDocument();
      System.out.println("\n" + doc.file);
      doc.printVector();
    }
  }

}

	
	
