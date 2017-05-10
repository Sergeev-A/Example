package ru.rbs.manifest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/")
public class MyServer extends HttpServlet {
    private static List<String> manifest = new ArrayList<>();
    private static final String FILE_NAME_TO_SEARCH = "manifest.mf";
    private static List<String> result = new ArrayList<>();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        try {

            URL url = new URL("http://localhost:8080/dashboard");

            ServletContext servletContext = getServletContext();
            String warpath = servletContext.getRealPath("/WEB-INF/config.xml");
            String directory = warpath.substring(0, warpath.indexOf("jetty-0.0.0.0"));
            searchDirectory(new File(directory));
            for (int i = 0; i < result.size(); i++) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(result.get(i))))) {
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    StringBuilder s = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        s.append(line);
                        s.append("<br>");
                    }
                    reader.close();
                    manifest.add(s.toString());
                    connection.setRequestProperty("time", "2135");
                    connection.setRequestProperty("name", "name");
                    connection.setRequestProperty("manifest", manifest.get(i));
                    connection.getOutputStream().write(manifest.get(i).getBytes());


                } catch (IOException e) {
                    //logger.error("unexpected exception", e);
                }
            }
        }
        catch (IOException e) {
            //logger.error("Unexpected exception", e);

        }
    }


    private static void searchDirectory(File file) {
        if (file.isDirectory()) {
            search(file);
        } else {}
            //logger.error(file.getAbsoluteFile() + " is not a directory\n");
    }

    private static void search(File file) {
        if (file.isDirectory()) {
            //logger.info("Searching directory " + file.getAbsoluteFile());
            if (file.canRead()) {
                for (File temp : file.listFiles())
                    if (temp.isDirectory()) {
                        search(temp);
                    } else if (FILE_NAME_TO_SEARCH.equals(temp.getName().toLowerCase()) && !(result.contains(temp.getAbsoluteFile().toString()))) {
                        result.add(temp.getAbsoluteFile().toString());
                    }
            } else
            {}
                //logger.error(file.getAbsoluteFile() + ": Permission Denied!");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}