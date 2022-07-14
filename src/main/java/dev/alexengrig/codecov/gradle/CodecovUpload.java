/*
 * Copyright 2020-2022 Alexengrig Dev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.alexengrig.codecov.gradle;

import dev.alexengrig.codecov.gradle.exception.ScriptFailureException;
import dev.alexengrig.codecov.gradle.service.CommandExecutor;
import dev.alexengrig.codecov.gradle.service.FileDownloader;
import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class CodecovUpload extends DefaultTask {
    private static final Logger log = Logging.getLogger(CodecovUpload.class);

    private final FileDownloader fileDownloader = new FileDownloader();
    private final CommandExecutor commandExecutor = new CommandExecutor(this::info);

    private final URL url;
    private final String filename;

    public CodecovUpload() throws MalformedURLException {
        url = URI.create("https://codecov.io/bash").toURL();
        filename = "codecov.sh";
    }

    @TaskAction
    public void execute() {
        info("Started.");
        File file = new File(filename);
        fileDownloader.download(url, file);
        String command = "bash " + file + " -Z";
        info("Executing command: " + command);
        int exitCode = commandExecutor.execute(command);
        if (exitCode != 0) {
            throw new ScriptFailureException(command, exitCode);
        }
        info("Finished.");
    }

    protected void info(String message) {
        log.info(message);
    }
}
