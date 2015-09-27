package peigen_temp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stemmer {
//	String repeatPattern = "([b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z][aeiouAEIOU])\\1[a-z]*";
	String prefixPattern = "\\^|\\s([Mm]a|[Nn]aka|[Pp]a|[Pp]ag|[Pp]agka|[Tt]ag-|[Nn]ag-*|[Mm]ay-|[Mm]aka|[Ii]|[Ii]ka|[Ii]pa|[Ii]pag|[Ii]pang|[Nn]aka|[Mm]agka|[Mm]agma|[Mm]agpaka|[Mm]agsi|[Mm]akapag|[Mm]akapang)[a-z]+";
	
	String[] prefixList = {"naka", "pagka", "pag","tag-", "nag-", "nag", "may-", "maka", "ika", "ipina", "ipang", "ipag", "ipa", "magka"
			+"magma", "magsi", "makapang", "ma", "pa", "i"};

	public void getRootWord(String text){
		Pattern p = Pattern.compile(prefixPattern);
		
		Matcher m = p.matcher(text);
		
		while(m.find()){
			String rootword = getRootword(m.group());
//			if(!rootword.equals(""))
//				System.out.println();
		}
	}
	
	public String getRootword(String origWord){
		for(int i = 0; i < prefixList.length; i++)
		{
			if(origWord.trim().startsWith(prefixList[i]))
			{	
				System.out.println(prefixList[i] + " " + origWord.replace(prefixList[i], ""));
				 return origWord.replace(prefixList[i], "");
			}
		}
		
		return "";
	}
	
//	public void getRootWord(String[] wordlist){
//		for(int i = 0; i < wordlist.length; i++){
//			for(String prefix: prefixList)
//				if(wordlist[i].startsWith(prefix))
//				{
//					sysout
//				}
//		}
//	}
	
	public static void main(String args[])
	{
		String text = "Nakatanggap po tayo ng liham na ipinadala ni Mr. Eric Cochingyan, sa pamamagitan ng Segment Producer Reseacher Na Si Noemi Reyes ng programang &quot;Isumbong Mo Kay Tulfo.&quot; Ito poÂy isang panawagan sa mga kinauukulan tungkol sa nangyari sa mga anak ni G. Cochingyan. Ako po ang director ng programa ng kaibigan ni kaibigan ramon tulfo. Inilalathala ko ng buo ang liham na ipinakiusap sa atin. Mr. Tony Calvento Calvento Files, Pilipino Star Ngayon &quot;Isa po ako sa inyong mga masugid na mambabasa at nabatid ko ang inyong makabuluhang paghahatid impormasyon sa ating mga kababayan. Dahil dito, di na po ako nag-atubiling ipaabot sa inyo ang aking hinaing laban sa isang opisyal ng BFP District-1 (San Lazaro) na si Fire Marshal Supt. Pablito Cordeta at iba pa niyang mga kasabwat. Dahil sa kanilang pagwawalang-bahala at pagtatakip, naantala ang isang mahalagang imbestigasyon. Ito po ang mga pagkakasunud-sunod ng mga pangyayari na makapagpapaliwanag o makapagpapatunay ng aking hinaing: Noong ika-24 ng Disyembre, 2002, ang aking mga anak na sina Fortune (9 taon) at Brandon (5 taon) ay nasawi nang masunog ang aming inuupahang apartment (sa halagang P 14,000 kada duwan) sa ikalimang palapag ng Li Seng Giap Bldg. (LSG) sa Binondo, MaynilaÂ kahit pa napakalapit ng mga bumbero dito: ang Kagawaran ng BFP ay ilang bloke lamang ang layo sa lugar na pinangyarihan at ang isang volunteer brigade ay halos katabi lamang. Ang inirereklamo ko po ay ang mga kabalastugang nag-uugnay tungkol sa fire safety ng LSG Bldg at ang investigation report (o kawalan nito) pagkatapos ng mga pangyayari na hindi makatarungan. Nasawi ang aking mga anak nang hindi sila makalabas dahil ang LSG ay walang fire escape, firewall, sprinkler system, outside window, wet standpipe at iba pang mga kinakailangan ng isang gusali, na naaayon sa batasÂ Nahirapang makapasok ang mga bumbero dahil imbes na dalawa ang gate na gumagana, iisa lang at maliban dyan walang lagusan sa ikaapat at ikalimang palapag. Si Supt. Cordeta ang unang humawak ng imbestigation at siya ring may responsibilidad sa pagaapruba ng &quot;Fire Safety Inspection Certificate&quot; sa lugar na ito. Kaya masasabing isang napakalaking &quot;cover-up&quot; o pagtakip nang: 1. biglaang pagkakaroon ng Fire Clearance para maisagawa ang mabilisang paglilinis ng lugar kahit wala pang ulat ng imbestigasyon 2. walang ulat na nagsasaad ukol sa kawalan ng ; fire safety ; ng naturang gusali. Isang malaking kasinungalingan ang sinagot ni BFP Supt. Cordeta sa isang panayam sa radio program ni Mon Tulfo noong nakaraang Hulyo 2003, na &quot;hindi raw labag sa batas&quot; ang ginawa ng LSG BLDG. Na walang fire escape, outside window at fire wall sa pagitan ng magkakatabing apartmentsÂ kaya pala baluktot mangatwiran ang opisyal na ito ay dahil sa may tinatago siyang kalokohan: &quot;Bagamat ang LSG Bldg. Ay limang palapag (ayon sa Certificate of Occupancy), ito ay pinalabas na apat lamang sa Fire Safety Inspection Certificate na ginawa ng opisina niya, para malibre sa pagkakabit ng tinatawag na &quot;sprinkler system&quot; ang LSG Bldg.&quot; Ika-1 ng Nobyembre 2003, nakita ko po mula sa labas ng LSG na tapos na ang pagpapagawa sa nasunog na mga apartment (5th flr), sa kabila ng utos ng Manila City Hall Bldg. Official na ihininto ang pagpapaayos dito habang patuloy pa ang isinasagawang imbestigasyon. Lumalabas din po na ang mga nasunog na mga apartment (5th flr), sa kabila ng utos ng Manila City Hall Bldg. Official na ihinto ang pagpapaayos dito habang patuloy pa ang isinasagawang imbestigasyon. Lumalabas din po na ang mga nasunog na apartment ay wala palang &quot; Approved Bldg. Plan &quot; nang itinayo. Sa kasalukuyan, may imbestigasyong isinasagawa ukol sa pangyayari at ganon din sa sinasabing &quot;alanganing pag-imbestiga&quot; ni Supt. Cordeta pati na ang pagsisinungaling niya sa panayam sa radyo. Nais ko lamang ay lumabas ang &quot;tamang ulat sa mga re-investigation report&quot;.) Maaari po kayang maisulat ninyo ang tungkol sa karanasan ko? Pag lumabas sa diyaryo, Nabubunyag ang mga di tamang gawain ng mga tiwaling opisyalÂ Sigurado po ako na pag lumabas ang tamang ulat, ito ay makakatulong sa marami Â lalo na sa mga nangungupahan lamang. Umaasa, Eric Cochingyan MAARI BA, PAKIAKSYUNAN ANG PAKIUSAP NG GINOONG ITO. DALAWANG BATA ANG NASAWI SA SUNOG NA ITO KUNG NASUNOD LAMANG ANG MGA REQUIREMENTS SA PAGBIBIGAY NG BLDG., PERMITS. MAY SULAT DIN SI G. COCHINGYAN PARA KAY MAYOR LITO ATIENZA. PARA SA ANUMANG COMMENTS O REACTIONS, MAARI KAYONG TUMAWAG SA;CALVENTO FILES&quot; 7788442. MAARI DIN KAYONG MAG-TEXT SA 09179904918. SA GUSTONG LUMIHAM, PADALA SA AKING EMAIL, tocal13@yahoo.com.";
		
		Stemmer s = new Stemmer();
		
		//String[] wordlist = text.split(" ");
		
		s.getRootWord(text);
		
		System.out.println("done");
	}
	
}
