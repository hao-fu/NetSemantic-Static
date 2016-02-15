package fu.hao.trust.dvm;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: DexFileFormat
 * @Description: int == int, long = long
 * @author: hao
 * @date: Feb 15, 2016 12:14:23 AM
 */
public class DexFileFormat {
	// ---------------------------------
	// map_list
	class map_item {
		int type;
		int unused;
		long size;
		long offset;
	}

	class map_list {
		long size;
		map_item map_item;
	}

	class type_item {
		int type_idx;
	}

	class type_list {
		long size;
		type_item type_item;
	}

	// ---------------------------------
	// string_ids
	class string_data_item {
		int index;
		int uleb128_len;
		int[] data = new int[255];
	}

	class string_ids {
		long string_data_off;
	}

	// ---------------------------------
	// type_ids
	class type_id_item {
		long descriptor_idx;
	}

	// ---------------------------------
	// proto_ids
	class proto_id_item {
		long shorty_idx; // pointer to string_data_item
		long return_type_idx; // pointer to type_ids
		long parameters_off;
	}

	// ---------------------------------
	// field_ids
	class field_id_item {
		int class_idx; /*
						 * index into the type_ids list for the definer of this
						 * field.
						 */
		int type_idx;
		long name_idx;
	}

	// ---------------------------------
	// method_ids
	class method_id_item {
		int class_idx; /*
						 * index into the type_ids list for the definer of this
						 * method.
						 */
		int proto_idx; /*
						 * index into the proto_ids list for the prototype of
						 * this method
						 */
		long name_idx;
	}

	// ---------------------------------
	// class defs
	class code_item {
		int registers_size;
		int ins_size;
		int outs_size;
		int tries_size;
		long debug_info_off;
		long insns_size;
		int[] insns;
		/*
		 * int padding; try_item handlers
		 */
	}

	class encoded_method {
		long method_idx_diff;
		long access_flags;
		long code_off;
		code_item code_item;
	}

	class class_def_item {
		long class_idx;
		long access_flags;
		long superclass_idx; /* index into typeIds for superclass */
		long interfaces_off; /* file offset to DexTypeList */
		long source_file_idx; /* index into stringIds for source file name */
		long annotations_off; /* file offset to annotations_directory_item */
		long class_data_off; /* file offset to class_data_item */
		long static_values_off; /* file offset to DexEncodedArray */
	}

	class class_data_item {
		long static_fields_size;
		long instance_fields_size;
		long direct_methods_size;
		long virtual_methods_size;

		encoded_method direct_methods;
		encoded_method virtual_methods;
	}

	// ---------------------------------

	class DexHeader {
		int[] magic = new int[8]; /* includes version number */
		int[] checksum = new int[4]; /* adler32 checksum */
		int[] signature = new int[20]; /* SHA-1 hash */
		long fileSize; /* length of entire file */
		long headerSize; /* offset to start of next section */
		long endianTag;
		long linkSize;
		long linkOff;
		long mapOff;
		long stringIdsSize;
		long stringIdsOff;
		long typeIdsSize;
		long typeIdsOff;
		long protoIdsSize;
		long protoIdsOff;
		long fieldIdsSize;
		long fieldIdsOff;
		long methodIdsSize;
		long methodIdsOff;
		long classDefsSize;
		long classDefsOff;
		long dataSize;
		long dataOff;
	}

	DexHeader header;
	string_ids string_ids;
	string_data_item string_data_item;
	type_id_item type_id_item;
	proto_id_item proto_id_item;
	type_list proto_type_list;
	field_id_item field_id_item;
	method_id_item method_id_item;
	class_def_item class_def_item;
	class_data_item class_data_item;
	map_list map_list;
	type_list type_list;
	int[] data;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DexFileFormat.class);
	
	public DexFileFormat(String filePath) throws FileNotFoundException {
		DataInputStream fp = new DataInputStream(
	             new BufferedInputStream(
	                    new FileInputStream(
	                new File(filePath, "rb"))));
		int[] buf;
		
		
		
	}
	
	
	
	
	public static void printDexHeader(DexHeader dex) {
	    int i = 0;
	    logger.info("Magic Number = ");
	    for ( i = 0 ; i < 8 ; i++ ) {
	        logger.info("0x%02x ", dex.magic[i]);
	    }
	    logger.info("( "); 
	    for ( i = 0 ; i < 8 ; i++ ) {
	        if ( dex.magic[i] != '\n' ) {
	            logger.info("%c", dex.magic[i]);
	        } else {
	            logger.info("\\n");
	        }
	    }
	    logger.info(" )\n");
	    
	    logger.info("Checksum      = "); 
	    for ( i = 3 ; i >= 0 ; i-- ) {
	        logger.info("%02x ", dex.checksum[i]); 
	    }
	    logger.info("\n");
	    logger.info("HAO \n");
	    
	    logger.info("FileSize      = %4d (0x%04x)\n", dex.fileSize,dex.fileSize);
	    logger.info("headerSize    = %4d (0x%04x)\n", dex.headerSize, dex.headerSize);
	    logger.info("endianTag     = %4d (0x%04x)\n", dex.endianTag, dex.endianTag);
	    logger.info("linkSize      = %4d (0x%04x)\n", dex.linkSize, dex.linkSize);
	    logger.info("mapOff        = %4d (0x%04x)\n", dex.mapOff, dex.mapOff);
	    logger.info("stringIdsSize = %4d (0x%04x)\n", dex.stringIdsSize, dex.stringIdsSize);
	    logger.info("stringIdsOff  = %4d (0x%04x)\n", dex.stringIdsOff, dex.stringIdsOff);
	    logger.info("typeIdsSize   = %4d (0x%04x)\n", dex.typeIdsSize, dex.typeIdsSize);
	    logger.info("typeIdsOff    = %4d (0x%04x)\n", dex.typeIdsOff, dex.typeIdsOff);
	    logger.info("protoIdsSize  = %4d (0x%04x)\n", dex.protoIdsSize, dex.protoIdsSize);
	    logger.info("protoIdsOff   = %4d (0x%04x)\n", dex.protoIdsOff, dex.protoIdsOff);
	    logger.info("fieldIdsSize  = %4d (0x%04x)\n", dex.fieldIdsSize, dex.fieldIdsSize);
	    logger.info("fieldIdsOff   = %4d (0x%04x)\n", dex.fieldIdsOff, dex.fieldIdsOff);
	    logger.info("methodIdsSize = %4d (0x%04x)\n", dex.methodIdsSize, dex.methodIdsSize);
	    logger.info("methodIdsOff  = %4d (0x%04x)\n", dex.methodIdsOff, dex.methodIdsOff);
	    logger.info("classDefsSize = %4d (0x%04x)\n", dex.classDefsSize, dex.classDefsSize);
	    logger.info("classDefsOff  = %4d (0x%04x)\n", dex.classDefsOff, dex.classDefsOff);
	    logger.info("dataSize      = %4d (0x%04x)\n", dex.dataSize, dex.dataSize);
	    logger.info("dataOff       = %4d (0x%04x)\n", dex.dataOff, dex.dataOff);

	}

	public static void printDexFile(DexFileFormat dex ) {
	    printDexHeader(dex.header);
	}
	

}
