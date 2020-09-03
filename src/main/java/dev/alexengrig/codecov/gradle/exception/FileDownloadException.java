/*
 * Copyright 2020 Alexengrig Dev.
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

package dev.alexengrig.codecov.gradle.exception;

import java.io.File;
import java.net.URL;

public class FileDownloadException extends RuntimeException {
    public FileDownloadException(URL url, File file, Exception cause) {
        super(String.format("Failed to download file from %s to '%s'", url, file), cause);
    }
}