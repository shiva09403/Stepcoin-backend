// ----------------------------------------------------------------------------
// Copyright 2007-2016, GeoTelematic Solutions, Inc.
// All rights reserved
// ----------------------------------------------------------------------------
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// ----------------------------------------------------------------------------
// Description:
//  Various checksum calculations
// ----------------------------------------------------------------------------
// Change History:
//  2010/01/29  Martin D. Flynn
//     -Initial release
//  2012/12/24  Martin D. Flynn
//     -Added "calcCrcXOR8", "calcCrcSum8"
//  2015/05/03  Martin D. Flynn
//     -Added "calcCrc16_modbus" (preload of 0xFFFF)
//     -Added "calcCrcXmodem" (preload of 0x0000)
// ----------------------------------------------------------------------------
package org.opengts.util;

import java.io.File;

/**
*** Checksum tools
**/

public class Checksum
{

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    /* startup init */
    static {
        Checksum.initCrcCCITT();
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    public static byte TwosCompliment(byte b)
    {
        return (byte)((~(int)b & 0xFF) + 1);
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    public enum CRC {
        SUM8        ( 8, "Sum8"),
        XOR8        ( 8, "Xor8"),
        CRC16       (16, "Crc16[0x0000]"),
        CRC16_0000  (16, "Crc16[0x0000]"),
        MODBUS      (16, "Modbus"),
        CRC16_FFFF  (16, "Crc16[0xFFFF]"),
        CCITT       (16, "CCITT[0xFFFF]"),
        CCITT_FFFF  (16, "CCITT[0xFFFF]"),
        XMODEM      (16, "Xmodem"),
        CCITT_0000  (16, "CCITT[0x0000]"),
        CCITT_1D0F  (16, "CCITT[0x1D0F]"),
        CRC32       (32, "Crc32");
        private int    bb = 0;
        private String dd = null;
        CRC(int b, String d)       { bb = b; dd = d; }
        public int    getBitLen()  { return bb; }
        public long   getBitMask() { return (1L << bb) - 1L; }
        public String toString()   { return dd; }
    };

    /**
    *** Returns checksum based on specified algorithm
    *** @param crc  The CRC algorithm
    *** @param b    The byte array
    *** @param bOfs The offset into the byte array to begin CRC
    *** @param bLen The number of bytes to include in the CRC
    **/
    public static long calcChecksum(CRC crc, byte b[], int bOfs, int bLen)
    {

        /* CRC not specified? */
        if (crc == null) {
            return 0x0000L;
        }

        /* execute checksum */
        switch (crc) {
            case SUM8:
                return (long)Checksum.calcCrcSum8(b, bOfs, bLen) & 0xFFL;
            case XOR8:
                return (long)Checksum.calcCrcXOR8(b, bOfs, bLen) & 0xFFL;
            case CRC16:
            case CRC16_0000:
                return (long)Checksum.calcCrc16(b, bOfs, bLen) & 0xFFFFL;
            case MODBUS:
            case CRC16_FFFF:
                return (long)Checksum.calcCrc16_modbus(b, bOfs, bLen) & 0xFFFFL;
            case CCITT:
            case CCITT_FFFF:
                return (long)Checksum.calcCrcCCITT(b, bOfs, bLen) & 0xFFFFL;
            case XMODEM:
            case CCITT_0000:
                return (long)Checksum.calcCrcXmodem(b, bOfs, bLen) & 0xFFFFL;
            case CCITT_1D0F:
                return (long)Checksum.calcCrcCCITT_1D0F(b, bOfs, bLen) & 0xFFFFL;
            case CRC32:
                return Checksum.calcCrc32(b, bOfs, bLen) & 0xFFFFFFFFL;
        }

        /* will not reach here */
        Print.logError("*** Should not reach here! ***");
        return 0x0000;

    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // -- CRC-CCITT (0xFFFF) [x16 + x12 + x5 + 1]
    private static int crc_CCITT_Table[] = null;
    private static synchronized void initCrcCCITT()
    {
        if (crc_CCITT_Table == null) {
            crc_CCITT_Table = new int[256];
            for (int c = 0; c < 256; c++) {
                int fcs = 0;
                int x = (c << 8);
                for (int j = 0; j < 8; j++) {
                    if (((fcs ^ x) & 0x8000) != 0) {
                        fcs = (fcs << 1) ^ 0x1021;
                    } else { 
                        fcs = (fcs << 1);
                    }
                    x <<= 1;
                    fcs &= 0xFFFF;
                }
                crc_CCITT_Table[c] = fcs;
            }
        }
    }

    private static int _calcCrcCCITT(int preload, byte b[], int bOfs, int bLen)
    {
        int W = preload & 0xFFFF; // preload
        if (b != null) {

            /* initialize CRC table */
            if (crc_CCITT_Table == null) { Checksum.initCrcCCITT(); }

            /* adjust offset/length */
            int ofs = (bOfs <= 0)? 0 : (bOfs >= b.length)? b.length : bOfs;
            int len = ((bLen >= 0) && (bLen <= (b.length-ofs)))? bLen : (b.length-ofs);

            /* calc CRC */
            for (int c = 0; c < len; c++) {
                W = (crc_CCITT_Table[(b[c+ofs] ^ (W >>> 8)) & 0xFF] ^ (W << 8)) & 0xFFFF;
            }

        }
        return W;
    }

    // --------------------------------

    private static int CRCCCITT_PRELOAD_FFFF    = 0xFFFF; // standard crc16-ccitt

    public static int calcCrcCCITT(byte b[])
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_FFFF, b, 0, -1);
    }

    public static int calcCrcCCITT(byte b[], int bLen)
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_FFFF, b, 0, bLen);
    }

    public static int calcCrcCCITT(byte b[], int bOfs, int bLen)
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_FFFF, b, bOfs, bLen);
    }

    // --------------------------------

    private static int CRCCCITT_PRELOAD_0000    = 0x0000;
    private static int CRCCCITT_PRELOAD_XMODEM  = CRCCCITT_PRELOAD_0000;

    public static int calcCrcXmodem(byte b[])
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_0000, b, 0, -1);
    }

    public static int calcCrcXmodem(byte b[], int bLen)
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_0000, b, 0, bLen);
    }

    public static int calcCrcXmodem(byte b[], int bOfs, int bLen)
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_0000, b, bOfs, bLen);
    }

    // --------------------------------

    private static int CRCCCITT_PRELOAD_1D0F    = 0x1D0F;

    public static int calcCrcCCITT_1D0F(byte b[])
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_1D0F, b, 0, -1);
    }

    public static int calcCrcCCITT_1D0F(byte b[], int bLen)
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_1D0F, b, 0, bLen);
    }

    public static int calcCrcCCITT_1D0F(byte b[], int bOfs, int bLen)
    {
        return Checksum._calcCrcCCITT(CRCCCITT_PRELOAD_1D0F, b, bOfs, bLen);
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // CRC16

    private static int CRCtab16[] = {
        0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
        0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
        0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
        0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
        0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
        0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
        0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
        0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
        0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
        0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
        0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
        0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
        0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
        0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
        0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
        0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
        0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
        0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
        0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
        0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
        0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
        0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
        0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
        0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
        0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
        0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
        0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
        0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
        0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
        0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
        0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
        0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040,
    };

    private static int _calcCrc16(int preload, byte b[], int bOfs, int bLen)
    {
        int crc = preload & 0xFFFF;
        if (b != null) {

            /* adjust offset/length */
            int ofs = (bOfs <= 0)? 0 : (bOfs >= b.length)? b.length : bOfs;
            int len = ((bLen >= 0) && (bLen <= (b.length-ofs)))? bLen : (b.length-ofs);

            /* calc CRC */
            for (int c = 0; c < len; c++) {
                crc = ((crc >> 8) ^ CRCtab16[(crc ^ b[c+ofs]) & 0xFF]); // (crc>>>8) changed to (crc>>8)
            }

        }
        return (crc & 0xFFFF);  
    }

    // --------------------------------

    private static final int CRC16_PRELOAD_0000     = 0x0000;

    public static int calcCrc16(byte b[])
    {
        return Checksum._calcCrc16(CRC16_PRELOAD_0000, b, 0, -1);
    }

    public static int calcCrc16(byte b[], int bOfs, int bLen)
    {
        return Checksum._calcCrc16(CRC16_PRELOAD_0000, b, bOfs, bLen);
    }

    // --------------------------------

    private static final int CRC16_PRELOAD_MODBUS   = 0xFFFF;

    public static int calcCrc16_modbus(byte b[])
    {
        return Checksum._calcCrc16(CRC16_PRELOAD_MODBUS, b, 0, -1);
    }

    public static int calcCrc16_modbus(byte b[], int bOfs, int bLen)
    {
        return Checksum._calcCrc16(CRC16_PRELOAD_MODBUS, b, bOfs, bLen);
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
    *** Used by CITG02 (CRC16-ITU?) - (non standard?)
    **/
    private static int CRCtab16_1[] = {
        0x0000, 0x1189, 0x2312, 0x329B, 0x4624, 0x57AD, 0x6536, 0x74BF,
        0x8C48, 0x9DC1, 0xAF5A, 0xBED3, 0xCA6C, 0xDBE5, 0xE97E, 0xF8F7,
        0x1081, 0x0108, 0x3393, 0x221A, 0x56A5, 0x472C, 0x75B7, 0x643E,
        0x9CC9, 0x8D40, 0xBFDB, 0xAE52, 0xDAED, 0xCB64, 0xF9FF, 0xE876,
        0x2102, 0x308B, 0x0210, 0x1399, 0x6726, 0x76AF, 0x4434, 0x55BD,
        0xAD4A, 0xBCC3, 0x8E58, 0x9FD1, 0xEB6E, 0xFAE7, 0xC87C, 0xD9F5,
        0x3183, 0x200A, 0x1291, 0x0318, 0x77A7, 0x662E, 0x54B5, 0x453C,
        0xBDCB, 0xAC42, 0x9ED9, 0x8F50, 0xFBEF, 0xEA66, 0xD8FD, 0xC974,
        0x4204, 0x538D, 0x6116, 0x709F, 0x0420, 0x15A9, 0x2732, 0x36BB,
        0xCE4C, 0xDFC5, 0xED5E, 0xFCD7, 0x8868, 0x99E1, 0xAB7A, 0xBAF3,
        0x5285, 0x430C, 0x7197, 0x601E, 0x14A1, 0x0528, 0x37B3, 0x263A,
        0xDECD, 0xCF44, 0xFDDF, 0xEC56, 0x98E9, 0x8960, 0xBBFB, 0xAA72,
        0x6306, 0x728F, 0x4014, 0x519D, 0x2522, 0x34AB, 0x0630, 0x17B9,
        0xEF4E, 0xFEC7, 0xCC5C, 0xDDD5, 0xA96A, 0xB8E3, 0x8A78, 0x9BF1,
        0x7387, 0x620E, 0x5095, 0x411C, 0x35A3, 0x242A, 0x16B1, 0x0738,
        0xFFCF, 0xEE46, 0xDCDD, 0xCD54, 0xB9EB, 0xA862, 0x9AF9, 0x8B70,
        0x8408, 0x9581, 0xA71A, 0xB693, 0xC22C, 0xD3A5, 0xE13E, 0xF0B7,
        0x0840, 0x19C9, 0x2B52, 0x3ADB, 0x4E64, 0x5FED, 0x6D76, 0x7CFF,
        0x9489, 0x8500, 0xB79B, 0xA612, 0xD2AD, 0xC324, 0xF1BF, 0xE036,
        0x18C1, 0x0948, 0x3BD3, 0x2A5A, 0x5EE5, 0x4F6C, 0x7DF7, 0x6C7E,
        0xA50A, 0xB483, 0x8618, 0x9791, 0xE32E, 0xF2A7, 0xC03C, 0xD1B5,
        0x2942, 0x38CB, 0x0A50, 0x1BD9, 0x6F66, 0x7EEF, 0x4C74, 0x5DFD,
        0xB58B, 0xA402, 0x9699, 0x8710, 0xF3AF, 0xE226, 0xD0BD, 0xC134,
        0x39C3, 0x284A, 0x1AD1, 0x0B58, 0x7FE7, 0x6E6E, 0x5CF5, 0x4D7C,
        0xC60C, 0xD785, 0xE51E, 0xF497, 0x8028, 0x91A1, 0xA33A, 0xB2B3,
        0x4A44, 0x5BCD, 0x6956, 0x78DF, 0x0C60, 0x1DE9, 0x2F72, 0x3EFB,
        0xD68D, 0xC704, 0xF59F, 0xE416, 0x90A9, 0x8120, 0xB3BB, 0xA232,
        0x5AC5, 0x4B4C, 0x79D7, 0x685E, 0x1CE1, 0x0D68, 0x3FF3, 0x2E7A,
        0xE70E, 0xF687, 0xC41C, 0xD595, 0xA12A, 0xB0A3, 0x8238, 0x93B1,
        0x6B46, 0x7ACF, 0x4854, 0x59DD, 0x2D62, 0x3CEB, 0x0E70, 0x1FF9,
        0xF78F, 0xE606, 0xD49D, 0xC514, 0xB1AB, 0xA022, 0x92B9, 0x8330,
        0x7BC7, 0x6A4E, 0x58D5, 0x495C, 0x3DE3, 0x2C6A, 0x1EF1, 0x0F78,
    };

    public static int calcCrc16_1(byte b[])
    {
        if (!ListTools.isEmpty(b)) {
            return Checksum.calcCrc16_1(b, 0, b.length);
        } else {
            return 0;
        }
    }

    public static int calcCrc16_1(byte b[], int bOfs, int bLen)
    {
        int crc = 0x0000; // 0xFFFF;
        if (b != null) {

            // -- adjust offset/length 
            int ofs = (bOfs <= 0)? 0 : (bOfs >= b.length)? b.length : bOfs;
            int len = ((bLen >= 0) && (bLen <= (b.length-ofs)))? bLen : (b.length-ofs);

            // -- calc CRC 
            for (int c = 0; c < len; c++) {
                crc = (crc >> 8) ^ CRCtab16_1[(crc ^ b[c+ofs]) & 0xFF];
            }

        }
        return (~crc & 0xFFFF);  
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // CRC32

    private static final int CRC32_PRELOAD   = 0x0000;

    public static long calcCrc32(byte b[])
    {
        if (!ListTools.isEmpty(b)) {
            return Checksum._calcCrc32(CRC32_PRELOAD, b, 0, b.length);
        } else {
            return 0L;
        }
    }

    public static long calcCrc32(byte b[], int bOfs, int bLen)
    {
        return Checksum._calcCrc32(CRC32_PRELOAD, b, bOfs, bLen);
    }

    private static long _calcCrc32(int preload, byte b[], int bOfs, int bLen)
    {
        int crc = preload; // not used
        if (b != null) {

            // adjust offset/length 
            int ofs = (bOfs <= 0)? 0 : (bOfs >= b.length)? b.length : bOfs;
            int len = ((bLen >= 0) && (bLen <= (b.length-ofs)))? bLen : (b.length-ofs);

            // calc CRC 32
            java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
            crc32.update(b, ofs, len);
            return crc32.getValue(); // long

        }
        return crc;
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // CRC XOR-8

    public static byte calcCrcXOR8(byte b[])
    {
        if (!ListTools.isEmpty(b)) {
            return Checksum.calcCrcXOR8(b, 0, b.length);
        } else {
            return (byte)0;
        }
    }

    public static byte calcCrcXOR8(byte b[], int bOfs, int bLen)
    {
        int crc = 0x00;
        if (b != null) {

            // adjust offset/length 
            int ofs = (bOfs <= 0)? 0 : (bOfs >= b.length)? b.length : bOfs;
            int len = ((bLen >= 0) && (bLen <= (b.length-ofs)))? bLen : (b.length-ofs);

            // calc CRC XOR-8
            for (int s = ofs; s < (ofs + len); s++) {
                crc = (crc ^ (int)b[s]) & 0xFF;
            }

        }
        return (byte)crc;
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // CRC Sum-8

    public static byte calcCrcSum8(byte b[])
    {
        if (!ListTools.isEmpty(b)) {
            return Checksum.calcCrcSum8(b, 0, b.length);
        } else {
            return (byte)0;
        }
    }

    public static byte calcCrcSum8(byte b[], int bOfs, int bLen)
    {
        int crc = 0x00;
        if (b != null) {

            // adjust offset/length 
            int ofs = (bOfs <= 0)? 0 : (bOfs >= b.length)? b.length : bOfs;
            int len = ((bLen >= 0) && (bLen <= (b.length-ofs)))? bLen : (b.length-ofs);

            // calc CRC XOR-8
            for (int s = ofs; s < (ofs + len); s++) {
                crc = (crc + (int)b[s]) & 0xFF;
            }

        }
        return (byte)crc;
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    
    private static final String ARG_DATA[]    = { "data"   , "d"  };
    private static final String ARG_FILE[]    = { "file"   , "f"  };
    private static final String ARG_PRELOAD[] = { "preload", "pl" };

    public static void main(String args[])
    {
        RTConfig.setCommandLineArgs(args);
        String data    = RTConfig.getString(ARG_DATA,"");
        File   file    = RTConfig.getFile(ARG_FILE,null);
        int    preload = RTConfig.getInt(ARG_PRELOAD,0);

        /* get data bytes */
        byte dataB[] = null;
        if (!StringTools.isBlank(data)) {
            dataB = data.startsWith("0x")? StringTools.parseHex(data,null) : data.getBytes();
            Print.sysPrintln("ASCII         : " + StringTools.toStringValue(dataB,'.'));
            Print.sysPrintln("Hex           : 0x" + StringTools.toHexString(dataB));
        } else 
        if (file != null) {
            dataB = FileTools.readFile(file);
            Print.sysPrintln("File          : " + file + " [length " + ListTools.size(dataB) + "]");
        }

        // -- look through CRC algorithms
        for (Checksum.CRC crc : Checksum.CRC.class.getEnumConstants()) {
            long crcV = Checksum.calcChecksum(crc, dataB, 0, -1) & crc.getBitMask();
            String label = StringTools.padRight(crc.toString(),' ',14);
            Print.sysPrintln(label + ": 0x" + StringTools.toHexString(crcV,crc.getBitLen()));
        }

        // -- crcCCITT with custom preload
        if (preload != 0x0000) {
            long crc = Checksum._calcCrcCCITT(preload, dataB, 0, -1);
            String PL = "0x" + StringTools.toHexString(preload,16);
            Print.sysPrintln("CCITT["+PL+"] : 0x" + StringTools.toHexString(crc,16));
        }

        // -- CRC-16 with custom preload
        if (preload != 0x0000) {
            long crc = Checksum._calcCrc16(preload, dataB, 0, -1);
            String PL = "0x" + StringTools.toHexString(preload,16);
            Print.sysPrintln("CRC16["+PL+"] : 0x" + StringTools.toHexString(crc,16));
        }

        // -- CRC-16-Alt1
        {
            int crc16_1 = Checksum.calcCrc16_1(dataB);
            Print.sysPrintln("CRC16_1       : 0x" + StringTools.toHexString((long)crc16_1,16));
        }

        // -- CRC-16-Alt2a
        //{
        //    int crc16_2a = calcCrc16_2a(dataB);
        //    Print.sysPrintln("CRC 16(2a)        : 0x" + StringTools.toHexString((long)crc16_2a,16));
        //}

        // -- CRC-16-Alt2b
        //{
        //    int crc16_2b = calcCrc16_2b(dataB);
        //    Print.sysPrintln("CRC 16(2b)        : 0x" + StringTools.toHexString((long)crc16_2b,16));
        //}

    }
    
}
