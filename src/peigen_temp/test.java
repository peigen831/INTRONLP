package peigen_temp;

public class test {

	static String VOWELS = "aeiou";
	static String CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZ裝cdfghjklmnpqrstvwxyz�";
	static final String[] PREFIX = { "i", "ka", "ma", "mag", "mang", "na", "nag", "nang", "pa", "pag", "pang" };
    static final String[] SUFFIX = { "in", "an", "hin", "han" };
	
    private static boolean isAcceptable(String word) {
    	if (VOWELS.contains(word.charAt(0) + "")) {
    		if (word.length() >= 3 && hasConsonant(word)) {
    			return true;
    		}
    	}
    	else if (CONSONANTS.contains(word.charAt(0) + "")) {
    		if (word.length() >= 4 && hasVowel(word)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public static boolean hasVowel(String word){
		
		for(int i = 0; i < word.length(); i++)
			if(VOWELS.contains(Character.toString(word.charAt(i))))
				return true;
		
		return false;		
	}
	
	public static boolean hasConsonant(String word){
		
		for(int i = 0; i < word.length(); i++)
			if(CONSONANTS.contains(Character.toString(word.charAt(i))))
				return true;
		
		return false;	
	}
	
	public static String processWordWithInfix(String word, String infix){
		for(int i = 0; i < word.length()-2; i++)
		{
			//if the infix is as prefix, check it's followed by a vowel
			if(i == 0 && word.startsWith(infix) && VOWELS.contains(Character.toString(word.charAt(i+2))))
					return word.substring(i+2);
				
			//if the infix is as infix, check the before character is consonant and the after character is vowel
			else if (word.substring(i, i+2).equals(infix) && CONSONANTS.contains(Character.toString(word.charAt(i-1))) && VOWELS.contains(Character.toString(word.charAt(i+2))))
				return word.substring(0, i) + word.substring(i + 2);
		}

		return "";
	}
	
	private static String processWordWithPrefix(String word) { 
         for (String prefix : PREFIX) {
             if (word.startsWith(prefix)) {
                 return word.substring(prefix.length());
             }
         }
		return null; 
	}
	
	private static boolean hasPartialDuplicate(String word) { /* TODO */
		if(isAcceptable(word)){
			if(word.charAt(0) == word.charAt(1))
				return true;
			else if(word.substring(0, 2).equals(word.substring(2,4)))
				return true;
		}
				
		return false; 
	}
	
	private static String processWordWithPartialDuplicate(String word) { /* TODO */ 
		if(isAcceptable(word)){
			if(word.charAt(0) == word.charAt(1))
				return word.substring(1);
			else if(word.substring(0, 2).equals(word.substring(2,4)))
				return word.substring(2);
		}
		return null; 
	}
	
	private static String processWordWithSuffix(String word) { 
        for (String suffix : SUFFIX) {
            if (word.endsWith(suffix)) {
                return word.substring(0, word.length()-suffix.length());
            }
        }
		return word;
	}

	public static void main(String[] args) {
//		String a = "ipinagsama";
//		String b = "inom";
//		String c = "pinainom";
//		String d = "ininom";
//		String in = "in";
//		
//		System.out.println(processWordWithInfix(a, in));
//		System.out.println(processWordWithInfix(b, in));
//		System.out.println(processWordWithInfix(c, in));
//		System.out.println(processWordWithInfix(d, in));
		
//		String a = "magsaka";
//		String b = "kakain";
//		String c = "panglangoy";
//		String d = "dota";
//		String in = "badminton";
//		
//		System.out.println(processWordWithPrefix(a));
//		System.out.println(processWordWithPrefix(b));
//		System.out.println(processWordWithPrefix(c));
//		System.out.println(processWordWithPrefix(d));
		
//		String a = "sasali";
//		String b = "kakain";
//		String c = "aalis";
//		String d = "doota";
//		String in = "badminton";
//		
//		if(hasPartialDuplicate(a))
//			System.out.println(processWordWithPartialDuplicate(a));
//		if(hasPartialDuplicate(b))
//			System.out.println(processWordWithPartialDuplicate(b));
//		if(hasPartialDuplicate(c))
//			System.out.println(processWordWithPartialDuplicate(c));
//		if(hasPartialDuplicate(d))
//			System.out.println(processWordWithPartialDuplicate(d));
		
		String a = "hukuman";
		String b = "hapunan";
		String c = "digmaan";
		String d = "basahin";
		
		System.out.println(processWordWithSuffix(a));
		System.out.println(processWordWithSuffix(b));
		System.out.println(processWordWithSuffix(c));
		System.out.println(processWordWithSuffix(d));
		
	}

}
