/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.carbondata.processing.sortandgroupby.sortdata;

import java.io.File;

import org.apache.carbondata.core.datastorage.store.compression.SnappyCompression.SnappyByteCompression;

public class CompressedTempSortFileReader extends AbstractTempSortFileReader {

  /**
   * CompressedTempSortFileReader
   *
   * @param measureCount
   * @param dimensionCount
   * @param tempFile
   */
  public CompressedTempSortFileReader(int dimensionCount, int complexDimensionCount,
      int measureCount, File tempFile, int noDictionaryCount) {
    super(dimensionCount, complexDimensionCount, measureCount, tempFile, noDictionaryCount);
  }

  /**
   * below method will be used to get chunk of rows
   *
   * @return row
   */
  @Override public Object[][] getRow() {
    int recordLength = fileHolder.readInt(filePath);
    int byteArrayLength = fileHolder.readInt(filePath);
    byte[] byteArrayFromFile = SnappyByteCompression.INSTANCE
        .unCompress(fileHolder.readByteArray(filePath, byteArrayLength));
    return prepareRecordFromByteBuffer(recordLength, byteArrayFromFile);
  }
}
