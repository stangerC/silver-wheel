package com.silver.wheel.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 字符串工具类。主要引用了Spring的StringUtils类以及Apache Common包的StringUtils类。
 * 在各个方法的实现中，尽量避免出现新建String对象，仅仅使用作为参数传进的String对象、类的
 * 静态字符字面量以及StringBuilder。由于使用了StringBuilder，因此本类尽适用JDK 5.0以上的
 * 版本
 *
 * @author liaojian
 * @since 31 October 2008
 * @see org.springframework.util.StringUtils
 * @see org.apache.commons.lang.StringUtils
 */
public abstract class StringUtils {
	/**
	 * 空白字符串
	 */
	public static final String EMPTY_STRING = "";
	/**
	 * 未查找到时的返回值
	 */
	public static final int INDEX_NOT_FOUND = -1;
	
	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String TOP_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = '.';


	//---------------------------------------------------------------------
	// 字符串对象的一般工具方法。
	//---------------------------------------------------------------------
	
	/**
	 * 检查字符串是否为null或者长度为0
	 * 
	 * @param str
	 * 		需要进行检查的字符串
	 * @return
	 * 		如果字符串为null，或者长度为0，则返回true
	 * @see org.springframework.util.StringUtils#hasLength(String str)
	 * @see org.apache.commons.lang.StringUtils#isEmpty(String str)
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}
	/**
	 * 与isEmpty(String str)方法相反，检查一个字符串是否不为空且长度大于0
	 * @param str
	 * 		需要检查的字符串
	 * @return
	 * 		如果字符串部位为null且长度大于0，则返回true
	 * @see org.apache.commons.lang.StringUtils#isNotEmpty(String str) 				
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	/**
	 * 检查给定字符串是否为“空白”字符串。空白，是指字符串为null、长度为0或者包含的字符
	 * 均为空白字符。
	 * 
	 * @param str
	 * 		需要检查的字符串。
	 * @return
	 * 		如果字符串str为null、长度为0或者包含的字符均为空白字符，则返回true。
	 * 
	 * @see org.springframework.util.StringUtils#hasText(String)
	 * @see org.apache.commons.lang.StringUtils#isBlank(String str)
	 */
	public static boolean isBlank(String str) {
		if(isEmpty(str)) {
			return true;
		}
		//不使用String的trim方法去除空白字符再比较，因为那样会创建新的字符串对象，而
		//String的charAt(int index)方法仅仅比较String自身的字符数组，不会创建新
		//的对象。而且trim方法也是会从字符数组的第一个字符开始向后比较，碰到非空
		//字符后再从后向前比较。看JDK中String类trim()方法的实现
		for(int i = 0; i < str.length(); i ++) {
			if(!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 与isBlank相反，检查给定字符产是否部位“空白”字符串。
	 * @param str
	 * 		需要检查的字符串
	 * @return 
	 * 		如果字符产不为“空白”字符串，则返回<code>true<code>
	 * @see org.apache.commons.lang.StringUtils#isNotBlank(String str)
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);		
	}

	/**
	 * 检查给出的字符串是否包含空格
	 * @param 需要检查的字符串 (可以为 <code>null</code>)
	 * @return <code>true</code> 如果字符串不为空，而且最少包含一个
	 * 空格
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean containsWhitespace(String str) {
		if (!isEmpty(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

//	/**
//	 * Trim leading and trailing whitespace from the given String.
//	 * @param str the String to check
//	 * @return the trimmed String
//	 * @see java.lang.Character#isWhitespace
//	 */
//	public static String trimWhitespace(String str) {
//		if (!hasLength(str)) {
//			return str;
//		}
//		StringBuffer buf = new StringBuffer(str);
//		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
//			buf.deleteCharAt(0);
//		}
//		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
//			buf.deleteCharAt(buf.length() - 1);
//		}
//		return buf.toString();
//	}
//
//	/**
//	 * Trim leading whitespace from the given String.
//	 * @param str the String to check
//	 * @return the trimmed String
//	 * @see java.lang.Character#isWhitespace
//	 */
//	public static String trimLeadingWhitespace(String str) {
//		if (!hasLength(str)) {
//			return str;
//		}
//		StringBuffer buf = new StringBuffer(str);
//		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
//			buf.deleteCharAt(0);
//		}
//		return buf.toString();
//	}
//
//	/**
//	 * Trim trailing whitespace from the given String.
//	 * @param str the String to check
//	 * @return the trimmed String
//	 * @see java.lang.Character#isWhitespace
//	 */
//	public static String trimTrailingWhitespace(String str) {
//		if (!hasLength(str)) {
//			return str;
//		}
//		StringBuffer buf = new StringBuffer(str);
//		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
//			buf.deleteCharAt(buf.length() - 1);
//		}
//		return buf.toString();
//	}
//
//	/**
//	 * Trim <i>all</i> whitespace from the given String:
//	 * leading, trailing, and inbetween characters.
//	 * @param str the String to check
//	 * @return the trimmed String
//	 * @see java.lang.Character#isWhitespace
//	 */
//	public static String trimAllWhitespace(String str) {
//		if (!hasLength(str)) {
//			return str;
//		}
//		StringBuffer buf = new StringBuffer(str);
//		int index = 0;
//		while (buf.length() > index) {
//			if (Character.isWhitespace(buf.charAt(index))) {
//				buf.deleteCharAt(index);
//			}
//			else {
//				index++;
//			}
//		}
//		return buf.toString();
//	}
//
//
//	/**
//	 * Test if the given String starts with the specified prefix,
//	 * ignoring upper/lower case.
//	 * @param str the String to check
//	 * @param prefix the prefix to look for
//	 * @see java.lang.String#startsWith
//	 */
//	public static boolean startsWithIgnoreCase(String str, String prefix) {
//		if (str == null || prefix == null) {
//			return false;
//		}
//		if (str.startsWith(prefix)) {
//			return true;
//		}
//		if (str.length() < prefix.length()) {
//			return false;
//		}
//		String lcStr = str.substring(0, prefix.length()).toLowerCase();
//		String lcPrefix = prefix.toLowerCase();
//		return lcStr.equals(lcPrefix);
//	}
//
//	/**
//	 * Test if the given String ends with the specified suffix,
//	 * ignoring upper/lower case.
//	 * @param str the String to check
//	 * @param suffix the suffix to look for
//	 * @see java.lang.String#endsWith
//	 */
//	public static boolean endsWithIgnoreCase(String str, String suffix) {
//		if (str == null || suffix == null) {
//			return false;
//		}
//		if (str.endsWith(suffix)) {
//			return true;
//		}
//		if (str.length() < suffix.length()) {
//			return false;
//		}
//
//		String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
//		String lcSuffix = suffix.toLowerCase();
//		return lcStr.equals(lcSuffix);
//	}
//
//	/**
//	 * Count the occurrences of the substring in string s.
//	 * @param str string to search in. Return 0 if this is null.
//	 * @param sub string to search for. Return 0 if this is null.
//	 */
//	public static int countOccurrencesOf(String str, String sub) {
//		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
//			return 0;
//		}
//		int count = 0, pos = 0, idx = 0;
//		while ((idx = str.indexOf(sub, pos)) != -1) {
//			++count;
//			pos = idx + sub.length();
//		}
//		return count;
//	}
//
//	/**
//	 * Replace all occurences of a substring within a string with
//	 * another string.
//	 * @param inString String to examine
//	 * @param oldPattern String to replace
//	 * @param newPattern String to insert
//	 * @return a String with the replacements
//	 */
//	public static String replace(String inString, String oldPattern, String newPattern) {
//		if (inString == null) {
//			return null;
//		}
//		if (oldPattern == null || newPattern == null) {
//			return inString;
//		}
//
//		StringBuffer sbuf = new StringBuffer();
//		// output StringBuffer we'll build up
//		int pos = 0; // our position in the old string
//		int index = inString.indexOf(oldPattern);
//		// the index of an occurrence we've found, or -1
//		int patLen = oldPattern.length();
//		while (index >= 0) {
//			sbuf.append(inString.substring(pos, index));
//			sbuf.append(newPattern);
//			pos = index + patLen;
//			index = inString.indexOf(oldPattern, pos);
//		}
//		sbuf.append(inString.substring(pos));
//
//		// remember to append any characters to the right of a match
//		return sbuf.toString();
//	}
//
//	/**
//	 * Delete all occurrences of the given substring.
//	 * @param pattern the pattern to delete all occurrences of
//	 */
//	public static String delete(String inString, String pattern) {
//		return replace(inString, pattern, "");
//	}
//
//	/**
//	 * Delete any character in a given string.
//	 * @param charsToDelete a set of characters to delete.
//	 * E.g. "az\n" will delete 'a's, 'z's and new lines.
//	 */
//	public static String deleteAny(String inString, String charsToDelete) {
//		if (inString == null || charsToDelete == null) {
//			return inString;
//		}
//		StringBuffer out = new StringBuffer();
//		for (int i = 0; i < inString.length(); i++) {
//			char c = inString.charAt(i);
//			if (charsToDelete.indexOf(c) == -1) {
//				out.append(c);
//			}
//		}
//		return out.toString();
//	}
//
//
//	//---------------------------------------------------------------------
//	// Convenience methods for working with formatted Strings
//	//---------------------------------------------------------------------
//
//	/**
//	 * Quote the given String with single quotes.
//	 * @param str the input String (e.g. "myString")
//	 * @return the quoted String (e.g. "'myString'"),
//	 * or <code>null<code> if the input was <code>null</code>
//	 */
//	public static String quote(String str) {
//		return (str != null ? "'" + str + "'" : null);
//	}
//
//	/**
//	 * Turn the given Object into a String with single quotes
//	 * if it is a String; keeping the Object as-is else.
//	 * @param obj the input Object (e.g. "myString")
//	 * @return the quoted String (e.g. "'myString'"),
//	 * or the input object as-is if not a String
//	 */
//	public static Object quoteIfString(Object obj) {
//		return (obj instanceof String ? quote((String) obj) : obj);
//	}
//
//	/**
//	 * Unqualify a string qualified by a '.' dot character. For example,
//	 * "this.name.is.qualified", returns "qualified".
//	 * @param qualifiedName the qualified name
//	 */
//	public static String unqualify(String qualifiedName) {
//		return unqualify(qualifiedName, '.');
//	}
//
//	/**
//	 * Unqualify a string qualified by a separator character. For example,
//	 * "this:name:is:qualified" returns "qualified" if using a ':' separator.
//	 * @param qualifiedName the qualified name
//	 * @param separator the separator
//	 */
//	public static String unqualify(String qualifiedName, char separator) {
//		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
//	}
//
//	/**
//	 * Capitalize a <code>String</code>, changing the first letter to
//	 * upper case as per {@link Character#toUpperCase(char)}.
//	 * No other letters are changed.
//	 * @param str the String to capitalize, may be <code>null</code>
//	 * @return the capitalized String, <code>null</code> if null
//	 */
//	public static String capitalize(String str) {
//		return changeFirstCharacterCase(str, true);
//	}
//
//	/**
//	 * Uncapitalize a <code>String</code>, changing the first letter to
//	 * lower case as per {@link Character#toLowerCase(char)}.
//	 * No other letters are changed.
//	 * @param str the String to uncapitalize, may be <code>null</code>
//	 * @return the uncapitalized String, <code>null</code> if null
//	 */
//	public static String uncapitalize(String str) {
//		return changeFirstCharacterCase(str, false);
//	}
//
//	private static String changeFirstCharacterCase(String str, boolean capitalize) {
//		if (str == null || str.length() == 0) {
//			return str;
//		}
//		StringBuffer buf = new StringBuffer(str.length());
//		if (capitalize) {
//			buf.append(Character.toUpperCase(str.charAt(0)));
//		}
//		else {
//			buf.append(Character.toLowerCase(str.charAt(0)));
//		}
//		buf.append(str.substring(1));
//		return buf.toString();
//	}
//
//	/**
//	 * Extract the filename from the given path,
//	 * e.g. "mypath/myfile.txt" -> "myfile.txt".
//	 * @param path the file path (may be <code>null</code>)
//	 * @return the extracted filename, or <code>null</code> if none
//	 */
//	public static String getFilename(String path) {
//		if (path == null) {
//			return null;
//		}
//		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
//		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
//	}
//
//	/**
//	 * Extract the filename extension from the given path,
//	 * e.g. "mypath/myfile.txt" -> "txt".
//	 * @param path the file path (may be <code>null</code>)
//	 * @return the extracted filename extension, or <code>null</code> if none
//	 */
//	public static String getFilenameExtension(String path) {
//		if (path == null) {
//			return null;
//		}
//		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
//		return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
//	}
//
//	/**
//	 * Strip the filename extension from the given path,
//	 * e.g. "mypath/myfile.txt" -> "mypath/myfile".
//	 * @param path the file path (may be <code>null</code>)
//	 * @return the path with stripped filename extension,
//	 * or <code>null</code> if none
//	 */
//	public static String stripFilenameExtension(String path) {
//		if (path == null) {
//			return null;
//		}
//		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
//		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
//	}
//
//	/**
//	 * Apply the given relative path to the given path,
//	 * assuming standard Java folder separation (i.e. "/" separators);
//	 * @param path the path to start from (usually a full file path)
//	 * @param relativePath the relative path to apply
//	 * (relative to the full file path above)
//	 * @return the full file path that results from applying the relative path
//	 */
//	public static String applyRelativePath(String path, String relativePath) {
//		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
//		if (separatorIndex != -1) {
//			String newPath = path.substring(0, separatorIndex);
//			if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
//				newPath += FOLDER_SEPARATOR;
//			}
//			return newPath + relativePath;
//		}
//		else {
//			return relativePath;
//		}
//	}
//
//	/**
//	 * Normalize the path by suppressing sequences like "path/.." and
//	 * inner simple dots.
//	 * <p>The result is convenient for path comparison. For other uses,
//	 * notice that Windows separators ("\") are replaced by simple slashes.
//	 * @param path the original path
//	 * @return the normalized path
//	 */
//	public static String cleanPath(String path) {
//		String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);
//
//		// Strip prefix from path to analyze, to not treat it as part of the
//		// first path element. This is necessary to correctly parse paths like
//		// "file:core/../core/io/Resource.class", where the ".." should just
//		// strip the first "core" directory while keeping the "file:" prefix.
//		int prefixIndex = pathToUse.indexOf(":");
//		String prefix = "";
//		if (prefixIndex != -1) {
//			prefix = pathToUse.substring(0, prefixIndex + 1);
//			pathToUse = pathToUse.substring(prefixIndex + 1);
//		}
//
//		String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
//		List pathElements = new LinkedList();
//		int tops = 0;
//
//		for (int i = pathArray.length - 1; i >= 0; i--) {
//			if (CURRENT_PATH.equals(pathArray[i])) {
//				// Points to current directory - drop it.
//			}
//			else if (TOP_PATH.equals(pathArray[i])) {
//				// Registering top path found.
//				tops++;
//			}
//			else {
//				if (tops > 0) {
//					// Merging path element with corresponding to top path.
//					tops--;
//				}
//				else {
//					// Normal path element found.
//					pathElements.add(0, pathArray[i]);
//				}
//			}
//		}
//
//		// Remaining top paths need to be retained.
//		for (int i = 0; i < tops; i++) {
//			pathElements.add(0, TOP_PATH);
//		}
//
//		return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
//	}
//
//	/**
//	 * Compare two paths after normalization of them.
//	 * @param path1 First path for comparizon
//	 * @param path2 Second path for comparizon
//	 * @return whether the two paths are equivalent after normalization
//	 */
//	public static boolean pathEquals(String path1, String path2) {
//		return cleanPath(path1).equals(cleanPath(path2));
//	}
//
//	/**
//	 * Parse the given locale string into a <code>java.util.Locale</code>.
//	 * This is the inverse operation of Locale's <code>toString</code>.
//	 * @param localeString the locale string, following
//	 * <code>java.util.Locale</code>'s toString format ("en", "en_UK", etc).
//	 * Also accepts spaces as separators, as alternative to underscores.
//	 * @return a corresponding Locale instance
//	 */
//	public static Locale parseLocaleString(String localeString) {
//		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
//		String language = (parts.length > 0 ? parts[0] : "");
//		String country = (parts.length > 1 ? parts[1] : "");
//		String variant = (parts.length > 2 ? parts[2] : "");
//		return (language.length() > 0 ? new Locale(language, country, variant) : null);
//	}
//
//
//	//---------------------------------------------------------------------
//	// Convenience methods for working with String arrays
//	//---------------------------------------------------------------------
//
//	/**
//	 * Append the given String to the given String array, returning a new array
//	 * consisting of the input array contents plus the given String.
//	 * @param array the array to append to (can be <code>null</code>)
//	 * @param str the String to append
//	 * @return the new array (never <code>null</code>)
//	 */
//	public static String[] addStringToArray(String[] array, String str) {
//		if (ObjectUtils.isEmpty(array)) {
//			return new String[] {str};
//		}
//		String[] newArr = new String[array.length + 1];
//		System.arraycopy(array, 0, newArr, 0, array.length);
//		newArr[array.length] = str;
//		return newArr;
//	}
//
//	/**
//	 * Concatenate the given String arrays into one,
//	 * with overlapping array elements included twice.
//	 * <p>The order of elements in the original arrays is preserved.
//	 * @param array1 the first array (can be <code>null</code>)
//	 * @param array2 the second array (can be <code>null</code>)
//	 * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
//	 */
//	public static String[] concatenateStringArrays(String[] array1, String[] array2) {
//		if (ObjectUtils.isEmpty(array1)) {
//			return array2;
//		}
//		if (ObjectUtils.isEmpty(array2)) {
//			return array1;
//		}
//		String[] newArr = new String[array1.length + array2.length];
//		System.arraycopy(array1, 0, newArr, 0, array1.length);
//		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
//		return newArr;
//	}
//
//	/**
//	 * Merge the given String arrays into one, with overlapping
//	 * array elements only included once.
//	 * <p>The order of elements in the original arrays is preserved
//	 * (with the exception of overlapping elements, which are only
//	 * included on their first occurence).
//	 * @param array1 the first array (can be <code>null</code>)
//	 * @param array2 the second array (can be <code>null</code>)
//	 * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
//	 */
//	public static String[] mergeStringArrays(String[] array1, String[] array2) {
//		if (ObjectUtils.isEmpty(array1)) {
//			return array2;
//		}
//		if (ObjectUtils.isEmpty(array2)) {
//			return array1;
//		}
//		List result = new ArrayList();
//		result.addAll(Arrays.asList(array1));
//		for (int i = 0; i < array2.length; i++) {
//			String str = array2[i];
//			if (!result.contains(str)) {
//				result.add(str);
//			}
//		}
//		return toStringArray(result);
//	}
//
//	/**
//	 * Turn given source String array into sorted array.
//	 * @param array the source array
//	 * @return the sorted array (never <code>null</code>)
//	 */
//	public static String[] sortStringArray(String[] array) {
//		if (ObjectUtils.isEmpty(array)) {
//			return new String[0];
//		}
//		Arrays.sort(array);
//		return array;
//	}
//
//	/**
//	 * Copy the given Collection into a String array.
//	 * The Collection must contain String elements only.
//	 * @param collection the Collection to copy
//	 * @return the String array (<code>null</code> if the passed-in
//	 * Collection was <code>null</code>)
//	 */
//	public static String[] toStringArray(Collection collection) {
//		if (collection == null) {
//			return null;
//		}
//		return (String[]) collection.toArray(new String[collection.size()]);
//	}
//
//	/**
//	 * Remove duplicate Strings from the given array.
//	 * Also sorts the array, as it uses a TreeSet.
//	 * @param array the String array
//	 * @return an array without duplicates, in natural sort order
//	 */
//	public static String[] removeDuplicateStrings(String[] array) {
//		if (ObjectUtils.isEmpty(array)) {
//			return array;
//		}
//		Set set = new TreeSet();
//		for (int i = 0; i < array.length; i++) {
//			set.add(array[i]);
//		}
//		return toStringArray(set);
//	}
//
//	/**
//	 * Split a String at the first occurrence of the delimiter.
//	 * Does not include the delimiter in the result.
//	 * @param toSplit the string to split
//	 * @param delimiter to split the string up with
//	 * @return a two element array with index 0 being before the delimiter, and
//	 * index 1 being after the delimiter (neither element includes the delimiter);
//	 * or <code>null</code> if the delimiter wasn't found in the given input String
//	 */
//	public static String[] split(String toSplit, String delimiter) {
//		if (!hasLength(toSplit) || !hasLength(delimiter)) {
//			return null;
//		}
//		int offset = toSplit.indexOf(delimiter);
//		if (offset < 0) {
//			return null;
//		}
//		String beforeDelimiter = toSplit.substring(0, offset);
//		String afterDelimiter = toSplit.substring(offset + delimiter.length());
//		return new String[] {beforeDelimiter, afterDelimiter};
//	}
//
//	/**
//	 * Take an array Strings and split each element based on the given delimiter.
//	 * A <code>Properties</code> instance is then generated, with the left of the
//	 * delimiter providing the key, and the right of the delimiter providing the value.
//	 * <p>Will trim both the key and value before adding them to the
//	 * <code>Properties</code> instance.
//	 * @param array the array to process
//	 * @param delimiter to split each element using (typically the equals symbol)
//	 * @return a <code>Properties</code> instance representing the array contents,
//	 * or <code>null</code> if the array to process was null or empty
//	 */
//	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
//		return splitArrayElementsIntoProperties(array, delimiter, null);
//	}
//
//	/**
//	 * Take an array Strings and split each element based on the given delimiter.
//	 * A <code>Properties</code> instance is then generated, with the left of the
//	 * delimiter providing the key, and the right of the delimiter providing the value.
//	 * <p>Will trim both the key and value before adding them to the
//	 * <code>Properties</code> instance.
//	 * @param array the array to process
//	 * @param delimiter to split each element using (typically the equals symbol)
//	 * @param charsToDelete one or more characters to remove from each element
//	 * prior to attempting the split operation (typically the quotation mark
//	 * symbol), or <code>null</code> if no removal should occur
//	 * @return a <code>Properties</code> instance representing the array contents,
//	 * or <code>null</code> if the array to process was <code>null</code> or empty
//	 */
//	public static Properties splitArrayElementsIntoProperties(
//			String[] array, String delimiter, String charsToDelete) {
//
//		if (ObjectUtils.isEmpty(array)) {
//			return null;
//		}
//		Properties result = new Properties();
//		for (int i = 0; i < array.length; i++) {
//			String element = array[i];
//			if (charsToDelete != null) {
//				element = deleteAny(array[i], charsToDelete);
//			}
//			String[] splittedElement = split(element, delimiter);
//			if (splittedElement == null) {
//				continue;
//			}
//			result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
//		}
//		return result;
//	}
//
//	/**
//	 * Tokenize the given String into a String array via a StringTokenizer.
//	 * Trims tokens and omits empty tokens.
//	 * <p>The given delimiters string is supposed to consist of any number of
//	 * delimiter characters. Each of those characters can be used to separate
//	 * tokens. A delimiter is always a single character; for multi-character
//	 * delimiters, consider using <code>delimitedListToStringArray</code>
//	 * @param str the String to tokenize
//	 * @param delimiters the delimiter characters, assembled as String
//	 * (each of those characters is individually considered as delimiter).
//	 * @return an array of the tokens
//	 * @see java.util.StringTokenizer
//	 * @see java.lang.String#trim()
//	 * @see #delimitedListToStringArray
//	 */
//	public static String[] tokenizeToStringArray(String str, String delimiters) {
//		return tokenizeToStringArray(str, delimiters, true, true);
//	}
//
//	/**
//	 * Tokenize the given String into a String array via a StringTokenizer.
//	 * <p>The given delimiters string is supposed to consist of any number of
//	 * delimiter characters. Each of those characters can be used to separate
//	 * tokens. A delimiter is always a single character; for multi-character
//	 * delimiters, consider using <code>delimitedListToStringArray</code>
//	 * @param str the String to tokenize
//	 * @param delimiters the delimiter characters, assembled as String
//	 * (each of those characters is individually considered as delimiter)
//	 * @param trimTokens trim the tokens via String's <code>trim</code>
//	 * @param ignoreEmptyTokens omit empty tokens from the result array
//	 * (only applies to tokens that are empty after trimming; StringTokenizer
//	 * will not consider subsequent delimiters as token in the first place).
//	 * @return an array of the tokens (<code>null</code> if the input String
//	 * was <code>null</code>)
//	 * @see java.util.StringTokenizer
//	 * @see java.lang.String#trim()
//	 * @see #delimitedListToStringArray
//	 */
//	public static String[] tokenizeToStringArray(
//			String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
//
//		if (str == null) {
//			return null;
//		}
//		StringTokenizer st = new StringTokenizer(str, delimiters);
//		List tokens = new ArrayList();
//		while (st.hasMoreTokens()) {
//			String token = st.nextToken();
//			if (trimTokens) {
//				token = token.trim();
//			}
//			if (!ignoreEmptyTokens || token.length() > 0) {
//				tokens.add(token);
//			}
//		}
//		return toStringArray(tokens);
//	}
//
//	/**
//	 * Take a String which is a delimited list and convert it to a String array.
//	 * <p>A single delimiter can consists of more than one character: It will still
//	 * be considered as single delimiter string, rather than as bunch of potential
//	 * delimiter characters - in contrast to <code>tokenizeToStringArray</code>.
//	 * @param str the input String
//	 * @param delimiter the delimiter between elements (this is a single delimiter,
//	 * rather than a bunch individual delimiter characters)
//	 * @return an array of the tokens in the list
//	 * @see #tokenizeToStringArray
//	 */
//	public static String[] delimitedListToStringArray(String str, String delimiter) {
//		if (str == null) {
//			return new String[0];
//		}
//		if (delimiter == null) {
//			return new String[] {str};
//		}
//		List result = new ArrayList();
//		if ("".equals(delimiter)) {
//			for (int i = 0; i < str.length(); i++) {
//				result.add(str.substring(i, i + 1));
//			}
//		}
//		else {
//			int pos = 0;
//			int delPos = 0;
//			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
//				result.add(str.substring(pos, delPos));
//				pos = delPos + delimiter.length();
//			}
//			if (str.length() > 0 && pos <= str.length()) {
//				// Add rest of String, but not in case of empty input.
//				result.add(str.substring(pos));
//			}
//		}
//		return toStringArray(result);
//	}
//
//	/**
//	 * Convert a CSV list into an array of Strings.
//	 * @param str the input String
//	 * @return an array of Strings, or the empty array in case of empty input
//	 */
//	public static String[] commaDelimitedListToStringArray(String str) {
//		return delimitedListToStringArray(str, ",");
//	}
//
//	/**
//	 * Convenience method to convert a CSV string list to a set.
//	 * Note that this will suppress duplicates.
//	 * @param str the input String
//	 * @return a Set of String entries in the list
//	 */
//	public static Set commaDelimitedListToSet(String str) {
//		Set set = new TreeSet();
//		String[] tokens = commaDelimitedListToStringArray(str);
//		for (int i = 0; i < tokens.length; i++) {
//			set.add(tokens[i]);
//		}
//		return set;
//	}
//
//	/**
//	 * Convenience method to return a Collection as a delimited (e.g. CSV)
//	 * String. E.g. useful for <code>toString()</code> implementations.
//	 * @param coll the Collection to display
//	 * @param delim the delimiter to use (probably a ",")
//	 * @param prefix the String to start each element with
//	 * @param suffix the String to end each element with
//	 */
//	public static String collectionToDelimitedString(Collection coll, String delim, String prefix, String suffix) {
//		if (CollectionUtils.isEmpty(coll)) {
//			return "";
//		}
//		StringBuffer sb = new StringBuffer();
//		Iterator it = coll.iterator();
//		while (it.hasNext()) {
//			sb.append(prefix).append(it.next()).append(suffix);
//			if (it.hasNext()) {
//				sb.append(delim);
//			}
//		}
//		return sb.toString();
//	}
//
//	/**
//	 * Convenience method to return a Collection as a delimited (e.g. CSV)
//	 * String. E.g. useful for <code>toString()</code> implementations.
//	 * @param coll the Collection to display
//	 * @param delim the delimiter to use (probably a ",")
//	 */
//	public static String collectionToDelimitedString(Collection coll, String delim) {
//		return collectionToDelimitedString(coll, delim, "", "");
//	}
//
//	/**
//	 * Convenience method to return a Collection as a CSV String.
//	 * E.g. useful for <code>toString()</code> implementations.
//	 * @param coll the Collection to display
//	 */
//	public static String collectionToCommaDelimitedString(Collection coll) {
//		return collectionToDelimitedString(coll, ",");
//	}
//
//	/**
//	 * Convenience method to return a String array as a delimited (e.g. CSV)
//	 * String. E.g. useful for <code>toString()</code> implementations.
//	 * @param arr the array to display
//	 * @param delim the delimiter to use (probably a ",")
//	 */
//	public static String arrayToDelimitedString(Object[] arr, String delim) {
//		if (ObjectUtils.isEmpty(arr)) {
//			return "";
//		}
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < arr.length; i++) {
//			if (i > 0) {
//				sb.append(delim);
//			}
//			sb.append(arr[i]);
//		}
//		return sb.toString();
//	}
//
//	/**
//	 * Convenience method to return a String array as a CSV String.
//	 * E.g. useful for <code>toString()</code> implementations.
//	 * @param arr the array to display
//	 */
//	public static String arrayToCommaDelimitedString(Object[] arr) {
//		return arrayToDelimitedString(arr, ",");
//	}	
}
