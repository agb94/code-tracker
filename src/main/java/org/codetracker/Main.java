package org.codetracker;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.codetracker.api.*;
import org.codetracker.change.Change;
import org.codetracker.element.Method;
import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitService;
import org.refactoringminer.util.GitServiceImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Main {

    public static void main(String args[]) throws Exception {
        // METHOD TRACKING

        String repoPath = args[0];
        String filePath = args[1];
        String startCommitId = args[2];
        String methodName = args[3];
        int startLine = Integer.parseInt(args[4]);
        String outPath = args[5];

        GitService gitService = new GitServiceImpl();
        try (Repository repository = gitService.openRepository(repoPath)){

            MethodTracker methodTracker = CodeTracker.methodTracker()
                    .repository(repository)
                    .filePath(filePath)
                    .startCommitId(startCommitId)
                    .methodName(methodName)
                    .methodDeclarationLineNumber(startLine)
                    .build();

            History<Method> methodHistory = methodTracker.track();

            List<Map<String, Object>> historyList = new ArrayList<>();

            for (History.HistoryInfo<Method> historyInfo : methodHistory.getHistoryInfoList()) {
                Map<String, Object> historyMap = new HashMap<>();
                historyMap.put("commitId", historyInfo.getCommitId());
                historyMap.put("commitDate", LocalDateTime.ofEpochSecond(historyInfo.getCommitTime(), 0, ZoneOffset.UTC).toString());
                historyMap.put("elementBeforeFilePath", historyInfo.getElementBefore().getFilePath());
                historyMap.put("elementBeforeFileName", historyInfo.getElementBefore().getName());
                historyMap.put("elementAfterFilePath", historyInfo.getElementAfter().getFilePath());
                historyMap.put("elementAfterName", historyInfo.getElementAfter().getName());
                
                List<String> changeList = new ArrayList<>();
                for (Change change : historyInfo.getChangeList()) {
                    changeList.add(change.getType().getTitle() + ": " + change);
                }
                historyMap.put("changeList", changeList);
    
                historyList.add(historyMap);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(historyList);
            System.out.println(response);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outPath))) {
                writer.write(response);
            }
        }
    }
}

