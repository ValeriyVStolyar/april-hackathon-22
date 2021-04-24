package LendingGenerator.Services;

import LendingGenerator.DAO.InputFields;
import LendingGenerator.MediaTypeUtils;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class StorageService {
    @Autowired
    private ServletContext servletContext;

    public String downloadFilledTemplate(String userName, String data) throws IOException {
        if (userName != null)
            CheckOrMakePath(userName);
        else return null;

        Gson gson = new Gson();
        InputFields[] res = gson.fromJson(data, InputFields[].class);

        try {
            File src = new File("WebPageTemplate");
            File out = new File("userFiles/" + userName);
            FileUtils.copyDirectory(src, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String indexFileText = generateIndexFile(res);

        try(FileWriter writer = new FileWriter("userFiles/" + userName+"/index.html", false))
        {
            writer.write(indexFileText);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        CheckOrMakePath("output/"+userName);
        //Ниже создаю зип файл
        ZipUtil.pack(new File("userFiles/" + userName), new File("userFiles/output/" + userName + "/yourPortfolio.zip"));
        //Так тут я генерирую ссылку на скачивание чтобы отдать на фронт

        return "http://localhost:8080/files/download?userName="+userName;
    }


    public void CheckOrMakePath(String foldersChain) {
        StringBuilder url = new StringBuilder("userFiles/");
        try {
            for (String folder : foldersChain.split("/")) {
                url.append(folder).append("/");
                if (!Files.exists(Paths.get(url.toString()))) {
                    Files.createDirectory(Paths.get(url.toString()));
                }
            }
        } catch (IOException e) {
        }
    }

    public String generateIndexFile(InputFields[] inputFields) {
        HashMap<String, String> myHashMap = new HashMap<String, String>();
        myHashMap.put("header__title", "07.12.1987");
        myHashMap.put("header__link", "07.12.1987");
        myHashMap.put("about__paragraph__first", "07.12.1987");
        myHashMap.put("about__paragraph__second", "07.12.1987");
        myHashMap.put("skills__skill-type__first", "07.12.1987");
        myHashMap.put("skills__skill-description", "07.12.1987");
        myHashMap.put("skills__skill-type__second", "07.12.1987");
        myHashMap.put("skills__skill-type__third", "07.12.1987");
        myHashMap.put("skills__skill-type__fours", "07.12.1987");
        myHashMap.put("skills__skill-type__five", "07.12.1987");
        myHashMap.put("skills__skill-type__six", "07.12.1987");
        myHashMap.put("skills__skill-type__seven", "07.12.1987");
        myHashMap.put("skills__skill-description__first", "07.12.1987");
        myHashMap.put("skills__skill-description__second", "07.12.1987");
        myHashMap.put("skills__skill-description__third", "07.12.1987");
        myHashMap.put("portfolio__subtitle", "07.12.1987");
        myHashMap.put("portfolio__paragraph", "07.12.1987");
        myHashMap.put("dribbble", "07.12.1987");
        myHashMap.put("behance", "07.12.1987");
        myHashMap.put("codepen", "07.12.1987");
        myHashMap.put("github", "07.12.1987");
        myHashMap.put("medium", "07.12.1987");
        myHashMap.put("linkedin", "07.12.1987");
        myHashMap.put("facebook", "07.12.1987");
        myHashMap.put("instagram", "07.12.1987");
        myHashMap.put("twitter", "07.12.1987");
        myHashMap.put("mail", "07.12.1987");
        myHashMap.put("tel", "07.12.1987");

        StringBuilder resultIndex = new StringBuilder();
        StringBuilder wordReader = new StringBuilder();
        try (FileReader reader = new FileReader("WebPageTemplate/index.html")) {
            int c;
            while ((c = reader.read()) != -1) {
                if (c == '$') {
                    c = reader.read();
                    if (c == '$') {
                        wordReader.setLength(0);
                        while ((c = reader.read()) != -1) {
                            if (c != '$')
                                wordReader.append((char) c);
                            else {
                                c = reader.read();
                                break;
                            }
                        }
                        boolean isIninputFields = false;

                        for (InputFields inputField : inputFields) {
                            if(inputField.key!= null)
                            if (inputField.key.equals(wordReader.toString())) {
                                isIninputFields = true;
                                resultIndex.append(inputField.value);
                                break;
                            }
                        }



                        if (!isIninputFields)
                            resultIndex.append(myHashMap.get(wordReader.toString()));
                    }
                } else {
                    resultIndex.append((char) c);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return resultIndex.toString();
    }

    public ResponseEntity<InputStreamResource> downloadFile(String userName) throws IOException {
        try {
            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, "yourPortfolio.zip");
            File file = new File("userFiles/output/"+userName+"/yourPortfolio.zip");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            String filenameFinal = new String("yourPortfolio.zip".getBytes("Cp1251" ), "Cp1252" );
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=yourPortfolio.zip")
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException ioException) {
            throw new IOException(ioException);
        }
    }

}
