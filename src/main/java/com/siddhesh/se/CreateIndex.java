package com.siddhesh.se;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class CreateIndex {
	public static void get_index(HashMap<Integer, HashMap<String, String>> docSet, String dir_path, Analyzer analyzer, Similarity similarity){
	    Date start = new Date();
	    try {
	      Directory dir = FSDirectory.open(Paths.get(dir_path));

	      IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
	      
	      iwc.setOpenMode(OpenMode.CREATE);
	      iwc.setSimilarity(similarity);
	      
	      IndexWriter writer = new IndexWriter(dir, iwc);
	      for (Map.Entry<Integer, HashMap<String, String>> entry : docSet.entrySet()) {
	        Document doc = new Document();
	        doc.add(new TextField("Id", Integer.toString(entry.getKey()), Field.Store.YES));
	        for(Map.Entry<String, String> properties : docSet.get(entry.getKey()).entrySet()) {
	        	doc.add(new TextField(properties.getKey(), properties.getValue(), Field.Store.YES));
	        	
	        }
	        writer.addDocument(doc);
	      }
	      writer.close();
	
	      Date end = new Date();
	      System.out.println(end.getTime() - start.getTime() + " Total ms");
	
	    } catch (IOException e) {
	    	System.out.println(e);
	    }
	}
}