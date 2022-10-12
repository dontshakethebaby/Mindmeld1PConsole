package cisc191;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CommonWords {

        public static String[] getCommonWords(String mainWord, int level) throws IOException, InterruptedException {
        mainWord = mainWord.toLowerCase();

        // the retrieval code was copied from https://zetcode.com/java/readwebpage/
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://relatedwords.org/relatedto/"+mainWord))
                .GET() // GET is default
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        // end of copied code

        // Data cleaning follows
        String sourceCode = response.body();
        String[] array = sourceCode.split("preloadedDataEl",2);
        sourceCode = array[1];
        array = sourceCode.split("score\":0.0",2);
        sourceCode = array[0];
        sourceCode = sourceCode.replaceAll("from","");
        sourceCode = sourceCode.replaceAll("cn5","");
        sourceCode = sourceCode.replaceAll("ol,","");
        sourceCode = sourceCode.replaceAll(",ol","");
        sourceCode = sourceCode.replaceAll("\"ol\"","");
        sourceCode = sourceCode.replaceAll(",wn","");
        sourceCode = sourceCode.replaceAll("wn,","");
        sourceCode = sourceCode.replaceAll("\"wn\"","");
        sourceCode = sourceCode.replaceAll("w2v","");
        sourceCode = sourceCode.replaceAll("swiki","");
        sourceCode = sourceCode.replaceAll("wiki","");
        sourceCode = sourceCode.replaceAll("score","");
        sourceCode = sourceCode.replace(':',' ');
        sourceCode = sourceCode.replace('\"',' ');
        sourceCode = sourceCode.replace('{',' ');
        sourceCode = sourceCode.replace('}',' ');
        sourceCode = sourceCode.replace(',',' ');
        sourceCode = sourceCode.replace('.',' ');
        sourceCode = sourceCode.replace('0',' ');
        sourceCode = sourceCode.replace('1',' ');
        sourceCode = sourceCode.replace('2',' ');
        sourceCode = sourceCode.replace('3',' ');
        sourceCode = sourceCode.replace('4',' ');
        sourceCode = sourceCode.replace('5',' ');
        sourceCode = sourceCode.replace('6',' ');
        sourceCode = sourceCode.replace('7',' ');
        sourceCode = sourceCode.replace('8',' ');
        sourceCode = sourceCode.replace('9',' ');
        array = sourceCode.split("word");


        /* This next section removes words that are invalid from the game's perspective such as
        more than 2 words ("Antarctic Treaty System"), or containing the main word ("strawberry"
        for "BERRY"). It also makes the array a standard size so each common words list is the same
        size for scoring fairness.
         */
        String prefix = mainWord;
         if (mainWord.length() > 6) {
             prefix = mainWord.substring(0, 5);     // First 5 letters
         }
         else { }

        String[] newArray = new String[level];
        int newIndex = 0;

        /* This loop removes whitespace around each word and conducts the validity checks.
        If the word passes these checks, it is placed in newArray, this process stops once
        the desired length of the array is reached. */
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
            /* str.split(findStr).length from
            https://stackoverflow.com/questions/767759/occurrences-of-substring-in-a-string */
            if ((array[i].split(" ").length > 2)|(WordManager.occurs(array[i],prefix))
                    |(WordManager.occurs(array[i],mainWord))) {
            }
            else {
                if (newIndex < level) {
                    newArray[newIndex] = array[i];
                    newIndex ++;
                }
                else break;
            }
        }
        return newArray;

    }

}
