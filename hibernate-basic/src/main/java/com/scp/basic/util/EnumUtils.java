package com.scp.basic.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class EnumUtils {

	public static List<Integer> enum2Ordinal(Class<? extends Enum> clz) {
		if (!clz.isEnum())
			return null;
		Enum[] enums = clz.getEnumConstants();
		List<Integer> rels = new ArrayList<Integer>();
		for (Enum en : enums) {
			rels.add(en.ordinal());
		}
		return rels;
	}

	public static List<String> enum2Name(Class<? extends Enum> clz) {
		if (!clz.isEnum())
			return null;
		Enum[] enums = clz.getEnumConstants();
		List<String> rels = new ArrayList<String>();
		for (Enum en : enums) {
			rels.add(en.name());
		}
		return rels;
	}

	public static Map<Integer, String> enum2BasicMap(Class<? extends Enum> clz) {
		if (!clz.isEnum())
			return null;
		Enum[] enums = clz.getEnumConstants();
		Map<Integer, String> rels = new HashMap<Integer, String>();
		for (Enum en : enums) {
			rels.put(en.ordinal(), en.name());
		}
		return rels;
	}

}
