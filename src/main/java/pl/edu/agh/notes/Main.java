package pl.edu.agh.notes;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Created by Michał Adamczyk.
 */
public class Main {

    static {
        DOMConfigurator.configure("log4j.xml");
    }
    
    
    public static void main(String[] args) throws IOException {
    	
    }
    
}
