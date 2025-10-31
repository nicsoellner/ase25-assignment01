import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommitMsgHook {
    public static void main(String[] args) {
        String commitMessage = args[0];

        StringBuilder stringBuilder = new StringBuilder();

        File config = new File("./scripts/commit-types.config");

        stringBuilder.append("(test|fix|feat|"); //Defaults

        //Get custom prefixes
        try (Scanner myReader = new Scanner(config)) {
            int i = 0;
            while (myReader.hasNextLine()) {
                stringBuilder.append(myReader.nextLine());
                stringBuilder.append("|");
                i++;
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(")");

        } catch (FileNotFoundException e) {
            System.out.println("No custom config file found");
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(")");
        }

        //Use regex from source cited in the lecture:
        //https://prahladyeri.github.io/blog/2019/06/how-to-enforce-conventional-commit-messages-using-git-hooks.html

        String conventionalCommitRegex = ("(!)?(\\([a-zA-Z0-9-]+\\))?:\\s[\\s\\S]+");


        stringBuilder.append(conventionalCommitRegex);

        String finalRegex = stringBuilder.toString();

        if (commitMessage.matches(finalRegex)) {
            System.out.println("Commit message is valid.");
            System.exit(0);
        } else {
            System.out.println("Commit message is invalid.");
            System.exit(1);
        }

    }
}
