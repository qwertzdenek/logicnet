package icp.tools;

import java.io.*;

public class BasicDataProvider implements DataProvider {

    private BufferedReader reader;
    public String fileName;

    public BasicDataProvider(String fileName) throws UnsupportedEncodingException, FileNotFoundException {
        this.fileName = fileName;
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
    }


    @Override
    public String[] next() throws IOException {
        String line = reader.readLine();
        if (line == null) return null;

        String[] words = line.split("[ \\t\\s\\n]+");
        return words;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

}
