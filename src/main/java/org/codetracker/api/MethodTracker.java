package org.codetracker.api;

import org.eclipse.jgit.lib.Repository;
import org.codetracker.MethodTrackerImpl;
import org.codetracker.element.Method;

public interface MethodTracker extends CodeTracker {

    History<Method> track() throws Exception;

    class Builder {
        private Repository repository;
        private String startCommitId;
        private String filePath;
        private String methodName;
        private int methodDeclarationLineNumber;

        public Builder repository(Repository repository) {
            this.repository = repository;
            return this;
        }

        public Builder startCommitId(String startCommitId) {
            this.startCommitId = startCommitId;
            return this;
        }

        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder methodDeclarationLineNumber(int methodDeclarationLineNumber) {
            this.methodDeclarationLineNumber = methodDeclarationLineNumber;
            return this;
        }

        private void checkInput() {

        }

        public MethodTracker build() {
            checkInput();
            return new MethodTrackerImpl(repository, startCommitId, filePath, methodName, methodDeclarationLineNumber);
        }

    }
}
