package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.impl.duration.impl.DataRecord.EPluralization;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.handlers.admission.GensmartCardDataHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IGensmartCardDataTransaction;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admission.GensmartCardDataTransactionimpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


public class GensmartCardDataHelper {
	private static final String FROM_DATEFORMAT="dd-MM-yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	/**
	 * Singleton object of GensmartCardDataHelper
	 */
	private static volatile GensmartCardDataHelper gensmartCardDataHelper = null;
	private static final Log log = LogFactory.getLog(GensmartCardDataHelper.class);
	private GensmartCardDataHelper() {
		
	}
	
	 private static Map<String,String> cityCodeMap = null;
	static {
		cityCodeMap = new HashMap<String, String>();
		cityCodeMap.put("ADILABAD", "AP805");
		cityCodeMap.put( "AGRA","UP260");
		cityCodeMap.put( "AHMADNAGAR","MH622");
		cityCodeMap.put( "AHMEDABAD","GJ570");
		cityCodeMap.put( "AIZAWL","MZ030");
		cityCodeMap.put( "AJMER","RJ510");
		cityCodeMap.put( "AKOLA","MH646");
		cityCodeMap.put( "ALAPUZHA","KL966");
		cityCodeMap.put( "ALIGARH","UP264");
		cityCodeMap.put( "ALLAHABAD","UP220");
		cityCodeMap.put( "ALMORA","UP282");
		cityCodeMap.put( "ALWAR","RJ502");
		cityCodeMap.put( "AMBALA","HR340");
		cityCodeMap.put( "AMRAVATI","MH644");
		cityCodeMap.put( "AMRELI","GJ574");
		cityCodeMap.put( "AMRITSAR","PB310");
		cityCodeMap.put( "ANANTAPUR","AP829");
		cityCodeMap.put( "ANANTNAG","JK444");
		cityCodeMap.put( "ANGUL","OR169");
		cityCodeMap.put( "ANNA","TN939");
		cityCodeMap.put( "ARARIA","BH083");
		cityCodeMap.put( "AURANGABAD","BH058");
		cityCodeMap.put( "AURANGABAD","MH624");
		cityCodeMap.put( "AZAMGARH","UP226");
		cityCodeMap.put( "BADGAM","JK432");
		cityCodeMap.put( "BAHRAICH","UP244");
		cityCodeMap.put( "BALAGHAT","MP752");
		cityCodeMap.put( "BALANGIR","OR184");
		cityCodeMap.put( "BALESHWAR","OR172");
		cityCodeMap.put( "BALLIA","UP235");
		cityCodeMap.put( "BANAS KANTHA","GJ544");
		cityCodeMap.put( "BANDA","UP218");
		cityCodeMap.put( "BANGALORE","KA840");
		cityCodeMap.put( "BANGALORE RURAL","KA842");
		cityCodeMap.put( "BANKA","BH046");
		cityCodeMap.put( "BANKURA","WB115");
		cityCodeMap.put( "BANSWARA","RJ534");
		cityCodeMap.put( "BARA BANKI","UP202");
		cityCodeMap.put( "BARAMULLA","JK442");
		cityCodeMap.put( "BARAN","RJ512");
		cityCodeMap.put( "BARDDHAMAN","WB120");
		cityCodeMap.put( "BAREILLY","UP250");
		cityCodeMap.put( "BARGARH","OR173");
		cityCodeMap.put( "BARMER","RJ523");
		cityCodeMap.put( "BARPETA","AS021");
		cityCodeMap.put( "BASTAR","MP762");
		cityCodeMap.put( "BASTI","UP242");
		cityCodeMap.put( "BATHINDA","PB304");
		cityCodeMap.put( "BEGUSARAI","BH077");
		cityCodeMap.put( "BELGAUM","KA870");
		cityCodeMap.put( "BELLARY","KA858");
		cityCodeMap.put( "BETUL","MP717");
		cityCodeMap.put( "BHABUA","BH037");
		cityCodeMap.put( "BHADOHI","UP231");
		cityCodeMap.put( "BHADRAK","OR171");
		cityCodeMap.put( "BHAGALPUR","BH080");
		cityCodeMap.put( "BHANDARA","MH638");
		cityCodeMap.put( "BHARATPUR","RJ504");
		cityCodeMap.put( "BHARUCH","GJ553");
		cityCodeMap.put( "BHAVNAGAR","GJ572");
		cityCodeMap.put( "BHILWARA","RJ508");
		cityCodeMap.put( "BHIND","MP739");
		cityCodeMap.put( "BHIWANI","HR359");
		cityCodeMap.put( "BHOJPUR","BH052");
		cityCodeMap.put( "BHOPAL","MP764");
		cityCodeMap.put( "BID","MH628");
		cityCodeMap.put( "BIDAR","KA866");
		cityCodeMap.put( "BIJAPUR","KA868");
		cityCodeMap.put( "BIJNOR","UP276");
		cityCodeMap.put( "BIKANER","RJ520");
		cityCodeMap.put( "BILASPUR","HP462");
		cityCodeMap.put( "BILASPUR","MP754");
		cityCodeMap.put( "BIRBHUM","WB122");
		cityCodeMap.put( "BISHENPUR","MN155");
		cityCodeMap.put( "BOKARO","BH035");
		cityCodeMap.put( "BONGAIGAON","AS002");
		cityCodeMap.put( "BOUDH","OR187");
		cityCodeMap.put( "BUDAUN","UP267");
		cityCodeMap.put( "BULANDSHAHR","UP268");
		cityCodeMap.put( "BULDHANA","MH648");
		cityCodeMap.put( "BUNDI","RJ536");
		cityCodeMap.put( "BUXAR","BH045");
		cityCodeMap.put( "CACHAR","AS022");
		cityCodeMap.put( "CHAMBA","HP472");
		cityCodeMap.put( "CHAMOLI","UP284");
		cityCodeMap.put( "CHANDEL","MN157");
		cityCodeMap.put( "CHANDIGARH","CH390");
		cityCodeMap.put( "CHANDRAPUR","MH636");
		cityCodeMap.put( "CHATRA","BH061");
		cityCodeMap.put( "CHENNAI","TN900");
		cityCodeMap.put( "CHHATARPUR","MP744");
		cityCodeMap.put( "CHHIMTUIPUI","MZ034");
		cityCodeMap.put( "CHHINDWARA","MP715");
		cityCodeMap.put( "CHIDAMBARANAR","TN933");
		cityCodeMap.put( "CHIKMAGALUR","KA877");
		cityCodeMap.put( "CHITRADURGA","KA856");
		cityCodeMap.put( "CHITTAURGARH","RJ535");
		cityCodeMap.put( "CHITTOOR","AP826");
		cityCodeMap.put( "CHUNGLANG","AR400");
		cityCodeMap.put( "CHURACHANDPUR","MN152");
		cityCodeMap.put( "CHURU","RJ516");
		cityCodeMap.put( "COIMBATORE","TN920");
		cityCodeMap.put( "CUDDALORE","TN906");
		cityCodeMap.put( "CUDDAPH","AP828");
		cityCodeMap.put( "CUTTACK","OR170");
		cityCodeMap.put( "DADRA & NAGAR HAVELI","DN698");
		cityCodeMap.put( "DAKSHIN DINAJPUR","WB131");
		cityCodeMap.put( "DAKSHIN KANNAD","KA880");
		cityCodeMap.put( "DAMAN","DD694");
		cityCodeMap.put( "DAMOH","MP708");
		cityCodeMap.put( "DANGS","GJ563");
		cityCodeMap.put( "DARBHANGA","BH074");
		cityCodeMap.put( "DARJILING","WB132");
		cityCodeMap.put( "DARRANG","AS007");
		cityCodeMap.put( "DATIA","MP742");
		cityCodeMap.put( "DAUSA","RJ509");
		cityCodeMap.put( "DAVANGERE","KA857");
		cityCodeMap.put( "DEHRA DUN","UP287");
		cityCodeMap.put( "DELHI","DL290");
		cityCodeMap.put( "DEOGARH","OR188");
		cityCodeMap.put( "DEOGHAR","BH040");
		cityCodeMap.put( "DEORIA","UP236");
		cityCodeMap.put( "DEWAS","MP719");
		cityCodeMap.put( "DHALAI","TP193");
		cityCodeMap.put( "DHANBAD","BH082");
		cityCodeMap.put( "DHAR","MP724");
		cityCodeMap.put( "DHARMAPURI","TN916");
		cityCodeMap.put( "DHARWAD","KA860");
		cityCodeMap.put( "DHEMAJI","AS410");
		cityCodeMap.put( "DHENKANAL","OR178");
		cityCodeMap.put( "DHOLPUR","RJ505");
		cityCodeMap.put( "DHUBRI","AS019");
		cityCodeMap.put( "DHULE","MH654");
		cityCodeMap.put( "DIBANG VALLEY","AR097");
		cityCodeMap.put( "DIBRUGARH","AS009");
		cityCodeMap.put( "DIU","DD696");
		cityCodeMap.put( "DODA","JK456");
		cityCodeMap.put( "DUMKA","BH041");
		cityCodeMap.put( "DUNGARPUR","RJ533");
		cityCodeMap.put( "DURG","MP750");
		cityCodeMap.put( "EAST GARO HILLS","ME023");
		cityCodeMap.put( "EAST GODAVARI","AP810");
		cityCodeMap.put( "EAST KAMENG","AR091");
		cityCodeMap.put( "EAST KHASI HILLS","ME026");
		cityCodeMap.put( "EAST NIMAR","MP718");
		cityCodeMap.put( "EAST SIANG","AR094");
		cityCodeMap.put( "EAST SIKKIM","SK196");
		cityCodeMap.put( "ERNAKULAM","KL970");
		cityCodeMap.put( "ETAH","UP266");
		cityCodeMap.put( "ETAWAH","UP256");
		cityCodeMap.put( "FAIZABAD","UP224");
		cityCodeMap.put( "FARIDABAD","HR361");
		cityCodeMap.put( "FARIDKOT","PB336");
		cityCodeMap.put( "FARRUKHABAD","UP254");
		cityCodeMap.put( "FATEHGARH SAHIB","PB318");
		cityCodeMap.put( "FATEHPUR","UP208");
		cityCodeMap.put( "FEROZPUR","PB306");
		cityCodeMap.put( "FIROZABAD","UP203");
		cityCodeMap.put( "GADCHIROLI","MH660");
		cityCodeMap.put( "GAJAPATI","OR161");
		cityCodeMap.put( "GANDHINAGAR","GJ540");
		cityCodeMap.put( "GANGANAGAR","RJ518");
		cityCodeMap.put( "GANJAM","OR162");
		cityCodeMap.put( "GARHWA","BH038");
		cityCodeMap.put( "GARHWAL","UP285");
		cityCodeMap.put( "GAUTAM BUDDHA NAGAR","UP271");
		cityCodeMap.put( "GAYA","BH070");
		cityCodeMap.put( "GHAZIABAD","UP269");
		cityCodeMap.put( "GHAZIPUR","UP234");
		cityCodeMap.put( "GIRIDIH","BH056");
		cityCodeMap.put( "GOALPARA","AS003");
		cityCodeMap.put( "GODDA","BH042");
		cityCodeMap.put( "GOLAGHAT","AS013");
		cityCodeMap.put( "GONDA","UP243");
		cityCodeMap.put( "GOPALGANJ","BH063");
		cityCodeMap.put( "GORAKHPUR","UP240");
		cityCodeMap.put( "GREATER MUMBAI","MH600");
		cityCodeMap.put( "GULBARGA","KA864");
		cityCodeMap.put( "GUMLA","BH043");
		cityCodeMap.put( "GUNA","MP736");
		cityCodeMap.put( "GUNTUR","AP822");
		cityCodeMap.put( "GURDASPUR","PB312");
		cityCodeMap.put( "GURGAON","HR348");
		cityCodeMap.put( "GWALIOR","MP740");
		cityCodeMap.put( "HAILAKANDI","AS004");
		cityCodeMap.put( "HAMIRPUR","HP482");
		cityCodeMap.put( "HAMIRPUR","UP216");
		cityCodeMap.put( "HANUMANGARH","RJ517");
		cityCodeMap.put( "HARDOI","UP206");
		cityCodeMap.put( "HARIDWAR","UP277");
		cityCodeMap.put( "HASSAN","KA846");
		cityCodeMap.put( "HAZARIBAG","BH084");
		cityCodeMap.put( "HISAR","HR350");
		cityCodeMap.put( "HOSHANGABAD","MP716");
		cityCodeMap.put( "HOSHIARPUR","PB332");
		cityCodeMap.put( "HOWRAH","WB110");
		cityCodeMap.put( "HUGLI","WB107");
		cityCodeMap.put( "HYDERABAD","AP800");
		cityCodeMap.put( "IDUKKI","KL976");
		cityCodeMap.put( "IMPHAL","MN158");
		cityCodeMap.put( "INDORE","MP720");
		cityCodeMap.put( "JABALPUR","MP710");
		cityCodeMap.put( "JAGATSINGHPUR","OR175");
		cityCodeMap.put( "JAINTIA HILLS","ME025");
		cityCodeMap.put( "JAIPUR","OR177");
		cityCodeMap.put( "JAIPUR","RJ500");
		cityCodeMap.put( "JAISALMER","RJ522");
		cityCodeMap.put( "JALANDHAR","PB320");
		cityCodeMap.put( "JALAUN","UP212");
		cityCodeMap.put( "JALGAON","MH656");
		cityCodeMap.put( "JALNA","MH625");
		cityCodeMap.put( "JALOR","RJ525");
		cityCodeMap.put( "JALPAIGURI","WB134");
		cityCodeMap.put( "JAMMU","JK450");
		cityCodeMap.put( "JAMNAGAR","GJ582");
		cityCodeMap.put( "JAMUI","BH047");
		cityCodeMap.put( "JAUNPUR","UP228");
		cityCodeMap.put( "JEHANABAD","BH081");
		cityCodeMap.put( "JHABUA","MP726");
		cityCodeMap.put( "JHALAWAR","RJ539");
		cityCodeMap.put( "JHANSI","UP214");
		cityCodeMap.put( "JHARSUGUDA","OR189");
		cityCodeMap.put( "JHUNJHUNUN","RJ515");
		cityCodeMap.put( "JIND","HR353");
		cityCodeMap.put( "JODHPUR","RJ530");
		cityCodeMap.put( "JORHAT","AS011");
		cityCodeMap.put( "JUNAGADH","GJ576");
		cityCodeMap.put( "KACHCHH","GJ586");
		cityCodeMap.put( "KAITHAL","HR354");
		cityCodeMap.put( "KAKROJHAR","AS020");
		cityCodeMap.put( "KALAHANDI","OR168");
		cityCodeMap.put( "KAMRUP","AS001");
		cityCodeMap.put( "KANCHEEPURAM","TN903");
		cityCodeMap.put( "KANGRA","HP470");
		cityCodeMap.put( "KANNUR","KL985");
		cityCodeMap.put( "KANPUR CITY","UP210");
		cityCodeMap.put( "KANPUR DEHAT","UP211");
		cityCodeMap.put( "KANYAKUMARI","TN936");
		cityCodeMap.put( "KAPURTHALA","PB314");
		cityCodeMap.put( "KARAIKAL","PN992");
		cityCodeMap.put( "KARBI ANGLONG","AS016");
		cityCodeMap.put( "KARGIL","JK438");
		cityCodeMap.put( "KARIMGANJ","AS017");
		cityCodeMap.put( "KARIMNAGAR","AP807");
		cityCodeMap.put( "KARNAL","HR342");
		cityCodeMap.put( "KASARAGOD","KL989");
		cityCodeMap.put( "KATHUA","JK452");
		cityCodeMap.put( "KATIHAR","BH071");
		cityCodeMap.put( "KENDRAPARA","OR179");
		cityCodeMap.put( "KEONJHAR","OR176");
		cityCodeMap.put( "KHAGARIA","BH085");
		cityCodeMap.put( "KHAMMAM","AP809");
		cityCodeMap.put( "KHEDA","GJ566");
		cityCodeMap.put( "KHURDA","OR181");
		cityCodeMap.put( "KINNAUR","HP476");
		cityCodeMap.put( "KISHANGANJ","BH049");
		cityCodeMap.put( "KOCH BIHAR","WB136");
		cityCodeMap.put( "KODAGU","KA882");
		cityCodeMap.put( "KODERMA","BH073");
		cityCodeMap.put( "KOHIMA","NG140");
		cityCodeMap.put( "KOLAR","KA852");
		cityCodeMap.put( "KOLHAPUR","MH608");
		cityCodeMap.put( "KOLKATA","WB100");
		cityCodeMap.put( "KOLLAM","KL963");
		cityCodeMap.put( "KORAPUT","OR166");
		cityCodeMap.put( "KOTA","RJ537");
		cityCodeMap.put( "KOTTAYAM","KL968");
		cityCodeMap.put( "KOZHIKODE","KL980");
		cityCodeMap.put( "KRISHNA","AP820");
		cityCodeMap.put( "KULU","HP468");
		cityCodeMap.put( "KUPWARA","JK436");
		cityCodeMap.put( "KURNOOL","AP830");
		cityCodeMap.put( "KURUKSHETRA","HR355");
		cityCodeMap.put( "LADAKH","JK458");
		cityCodeMap.put( "LAHUL & SPITI","HP474");
		cityCodeMap.put( "LAKHIMPUR","AS010");
		cityCodeMap.put( "LAKHIMPUR KHERI","UP247");
		cityCodeMap.put( "LAKHISARAI","BH075");
		cityCodeMap.put( "LAKSHADWEEP","LK890");
		cityCodeMap.put( "LALITPUR","UP213");
		cityCodeMap.put( "LATUR","MH662");
		cityCodeMap.put( "LOHARDAGGA","BH044");
		cityCodeMap.put( "LOHIT","AR092");
		cityCodeMap.put( "LOWER SUBANSIRI","AR095");
		cityCodeMap.put( "LUCKNOW","UP200");
		cityCodeMap.put( "LUDHIANA","PB330");
		cityCodeMap.put( "LUNGLEI","MZ032");
		cityCodeMap.put( "MADHEPURA","BH068");
		cityCodeMap.put( "MADHUBANI","BH054");
		cityCodeMap.put( "MADURAI","TN930");
		cityCodeMap.put( "MAHARAJGANJ","UP225");
		cityCodeMap.put( "MAHBUBNAGAR","AP834");
		cityCodeMap.put( "MAHE","PN994");
		cityCodeMap.put( "MAHENDRAGARH","HR346");
		cityCodeMap.put( "MAHESAN","GJ542");
		cityCodeMap.put( "MAHOBA","UP217");
		cityCodeMap.put( "MAINPURI","UP258");
		cityCodeMap.put( "MALAPPURAM","KL983");
		cityCodeMap.put( "MALDAH","WB128");
		cityCodeMap.put( "MALKANGIRI","OR163");
		cityCodeMap.put( "MANDI","HP464");
		cityCodeMap.put( "MANDLA","MP712");
		cityCodeMap.put( "MANDSAUR","MP728");
		cityCodeMap.put( "MANDYA","KA843");
		cityCodeMap.put( "MANSA","PB316");
		cityCodeMap.put( "MATHURA","UP262");
		cityCodeMap.put( "MAU","UP253");
		cityCodeMap.put( "MAYURBHANJ","OR174");
		cityCodeMap.put( "MEDAK","AP802");
		cityCodeMap.put( "MEDINIPUR","WB112");
		cityCodeMap.put( "MEERUT","UP270");
		cityCodeMap.put( "MIRZAPUR","UP232");
		cityCodeMap.put( "MOKOKCHUNG","NG142");
		cityCodeMap.put( "MON","NG146");
		cityCodeMap.put( "MORADABAD","UP278");
		cityCodeMap.put( "MORENA","MP738");
		cityCodeMap.put( "MORIGAON","AS005");
		cityCodeMap.put( "MUNGER","BH072");
		cityCodeMap.put( "MURSHIDABAD","WB126");
		cityCodeMap.put( "MUZAFFARNAGAR","UP272");
		cityCodeMap.put( "MUZAFFARPUR","BH062");
		cityCodeMap.put( "MYSORE","KA850");
		cityCodeMap.put( "NADIA","WB124");
		cityCodeMap.put( "NAGAON","AS014");
		cityCodeMap.put( "NAGAPATTINAM_Q_E_M","TN909");
		cityCodeMap.put( "NAGAUR","RJ511");
		cityCodeMap.put( "NAGPUR","MH640");
		cityCodeMap.put( "NAINITAL","UP281");
		cityCodeMap.put( "NALANDA","BH051");
		cityCodeMap.put( "NALBARI","AS008");
		cityCodeMap.put( "NALGONDA","AP832");
		cityCodeMap.put( "NANDED","MH632");
		cityCodeMap.put( "NARASIMHAPUR","MP714");
		cityCodeMap.put( "NASIK","MH650");
		cityCodeMap.put( "NAWADA","BH059");
		cityCodeMap.put( "NAWAPARA","OR185");
		cityCodeMap.put( "NAYAGARH","OR183");
		cityCodeMap.put( "NELLORE","AP824");
		cityCodeMap.put( "NICOBAR","AN195");
		cityCodeMap.put( "NILGIRI","TN918");
		cityCodeMap.put( "NIZAMABAD","AP803");
		cityCodeMap.put( "NORTH 24 PARGANAS","WB101");
		cityCodeMap.put( "NORTH CACHAR HILLS","AS018");
		cityCodeMap.put( "NORTH SIKKIM","SK198");
		cityCodeMap.put( "NORTH TRIPURA","TP190");
		cityCodeMap.put( "NORTH-GOA","GO690");
		cityCodeMap.put( "NOWRANGPUR","OR165");
		cityCodeMap.put( "OSMANABAD","MH630");
		cityCodeMap.put( "P.T. THIRUMAGAN","TN922");
		cityCodeMap.put( "PADRAUNA","UP237");
		cityCodeMap.put( "PAKUR","BH069");
		cityCodeMap.put( "PALAKKAD","KL974");
		cityCodeMap.put( "PALAMU","BH086");
		cityCodeMap.put( "PALI","RJ528");
		cityCodeMap.put( "PANCH MAHALS","GJ548");
		cityCodeMap.put( "PANCHKULA","HR345");
		cityCodeMap.put( "PANIPAT","HR343");
		cityCodeMap.put( "PANNA","MP745");
		cityCodeMap.put( "PAPUMPARE","AR401");
		cityCodeMap.put( "PARBHANI","MH626");
		cityCodeMap.put( "PASCHIMI CHAMPARAN","BH064");
		cityCodeMap.put( "PASCHIMI SINGHBHUM","BH088");
		cityCodeMap.put( "PATHANAMTHITTA","KL978");
		cityCodeMap.put( "PATIALA","PB300");
		cityCodeMap.put( "PATNA","BH060");
		cityCodeMap.put( "PERAMBALUR","TN914");
		cityCodeMap.put( "PERIYAR","TN924");
		cityCodeMap.put( "PHEK","NG148");
		cityCodeMap.put( "PHULABANI","OR164");
		cityCodeMap.put( "PILIBHIT","UP249");
		cityCodeMap.put( "PITHORAGARH","UP283");
		cityCodeMap.put( "PONDICHERRY","PN990");
		cityCodeMap.put( "POONCH","JK446");
		cityCodeMap.put( "PRAKASAM","AP825");
		cityCodeMap.put( "PRATAPGARH","UP222");
		cityCodeMap.put( "PUDUKKOTTAI","TN938");
		cityCodeMap.put( "PULWAMA","JK434");
		cityCodeMap.put( "PUNE","MH620");
		cityCodeMap.put( "PURBI CHAMPARAN","BH053");
		cityCodeMap.put( "PURBI SINGHBHUM","BH036");
		cityCodeMap.put( "PURI","OR160");
		cityCodeMap.put( "PURNIA","BH078");
		cityCodeMap.put( "PURULIYA","WB117");
		cityCodeMap.put( "RAI BARELI","UP204");
		cityCodeMap.put( "RAICHUR","KA862");
		cityCodeMap.put( "RAIGAD","MH603");
		cityCodeMap.put( "RAIGARH","MP758");
		cityCodeMap.put( "RAIPUR","CG760");
		cityCodeMap.put( "RAIPUR","MP760");
		cityCodeMap.put( "RAISEN","MP702");
		cityCodeMap.put( "RAJGARH","MP734");
		cityCodeMap.put( "RAJKOT","GJ580");
		cityCodeMap.put( "RAJNANDGAON","MP766");
		cityCodeMap.put( "RAJOURI","JK448");
		cityCodeMap.put( "RAJSAMAND","RJ514");
		cityCodeMap.put( "RAMANATHAPURAM","TN932");
		cityCodeMap.put( "RAMPUR","UP280");
		cityCodeMap.put( "RANCHI","BH087");
		cityCodeMap.put( "RANCHI","JH087");
		cityCodeMap.put( "RANGAREDDY","AP838");
		cityCodeMap.put( "RATLAM","MP727");
		cityCodeMap.put( "RATNAGIRI","MH605");
		cityCodeMap.put( "RAYAGADA","OR167");
		cityCodeMap.put( "REWA","MP747");
		cityCodeMap.put( "REWARI","HR347");
		cityCodeMap.put( "RI BHOI","ME028");
		cityCodeMap.put( "ROHTAK","HR344");
		cityCodeMap.put( "ROHTAS","BH067");
		cityCodeMap.put( "RUPNAGAR","PB334");
		cityCodeMap.put( "SABAR KANTHA","GJ546");
		cityCodeMap.put( "SAGAR","MP705");
		cityCodeMap.put( "SAHARANPUR","UP274");
		cityCodeMap.put( "SAHARSA","BH076");
		cityCodeMap.put( "SAHEBGANJ","BH039");
		cityCodeMap.put( "SALEM","TN913");
		cityCodeMap.put( "SAMASTIPUR","BH055");
		cityCodeMap.put( "SAMBALPUR","OR182");
		cityCodeMap.put( "SAMBUVARAYAR","TN919");
		cityCodeMap.put( "SANGLI","MH612");
		cityCodeMap.put( "SANGRUR","PB302");
		cityCodeMap.put( "SARAN","BH066");
		cityCodeMap.put( "SATARA","MH615");
		cityCodeMap.put( "SATNA","MP746");
		cityCodeMap.put( "SAWAI MADHOPUR","RJ506");
		cityCodeMap.put( "SEHORE","MP700");
		cityCodeMap.put( "SENAPATI","MN150");
		cityCodeMap.put( "SEONI","MP713");
		cityCodeMap.put( "SHAHDOL","MP749");
		cityCodeMap.put( "SHAHJAHANPUR","UP252");
		cityCodeMap.put( "SHAJAPUR","MP732");
		cityCodeMap.put( "SHEIKHPURA","BH079");
		cityCodeMap.put( "SHEOHAR","BH065");
		cityCodeMap.put( "SHIMOGA","KA874");
		cityCodeMap.put( "SHIVPURI","MP737");
		cityCodeMap.put( "SIBSAGAR","AS012");
		cityCodeMap.put( "SIDHARTHANAGAR","UP257");
		cityCodeMap.put( "SIDHI","MP748");
		cityCodeMap.put( "SIKAR","RJ513");
		cityCodeMap.put( "SIMLA","HP460");
		cityCodeMap.put( "SINDHUDURG","MH607");
		cityCodeMap.put( "SIRMAUR","HP480");
		cityCodeMap.put( "SIROHI","RJ526");
		cityCodeMap.put( "SIRSA","HR352");
		cityCodeMap.put( "SITAMARHI","BH050");
		cityCodeMap.put( "SITAPUR","UP245");
		cityCodeMap.put( "SIWAN","BH057");
		cityCodeMap.put( "SOLAN","HP486");
		cityCodeMap.put( "SOLAPUR","MH610");
		cityCodeMap.put( "SONBHADRA","UP288");
		cityCodeMap.put( "SONEPUR","OR186");
		cityCodeMap.put( "SONIPAT","HR357");
		cityCodeMap.put( "SONITPUR","AS006");
		cityCodeMap.put( "SOUTH 24 PARGANAS","WB103");
		cityCodeMap.put( "SOUTH GARO HILLS","ME029");
		cityCodeMap.put( "SOUTH SIKKIM","SK199");
		cityCodeMap.put( "SOUTH TRIPURA","TP192");
		cityCodeMap.put( "SOUTH-GOA","GO692");
		cityCodeMap.put( "SRIKAKULAM","AP815");
		cityCodeMap.put( "SRINAGAR","JK440");
		cityCodeMap.put( "SULTANPUR","UP223");
		cityCodeMap.put( "SUNDARGARH","OR180");
		cityCodeMap.put( "SUPAUL","BH048");
		cityCodeMap.put( "SURAT","GJ560");
		cityCodeMap.put( "SURENDRANAGAR","GJ584");
		cityCodeMap.put( "SURGUJA","MP756");
		cityCodeMap.put( "T.KATTABOMMAN","TN934");
		cityCodeMap.put( "TAMENGLONG","MN156");
		cityCodeMap.put( "TAWANG","AR099");
		cityCodeMap.put( "TEHRI GARHWAL","UP286");
		cityCodeMap.put( "THANE","MH601");
		cityCodeMap.put( "THANJAVUR","TN908");
		cityCodeMap.put( "THENI","TN931");
		cityCodeMap.put( "THIRUVALLUR","TN902");
		cityCodeMap.put( "THIRUVANANTHAPURAM","KL960");
		cityCodeMap.put( "THOUBAL","MN153");
		cityCodeMap.put( "THRISSUR","KL972");
		cityCodeMap.put( "TIKAMGARH","MP743");
		cityCodeMap.put( "TINSUKIA","AS015");
		cityCodeMap.put( "TIRAP","AR098");
		cityCodeMap.put( "TIRUCHIRAPALLI","TN910");
		cityCodeMap.put( "TIRUPUR","TN912");
		cityCodeMap.put( "TONK","RJ507");
		cityCodeMap.put( "TUENSANG","NG144");
		cityCodeMap.put( "TUMKUR","KA854");
		cityCodeMap.put( "UDAIPUR","RJ531");
		cityCodeMap.put( "UDHAMPUR","JK454");
		cityCodeMap.put( "UJJAIN","MP730");
		cityCodeMap.put( "UKHRUL","MN154");
		cityCodeMap.put( "UNA","HP484");
		cityCodeMap.put( "UNNAO","UP205");
		cityCodeMap.put( "UPPER SUBANSIRI","AR096");
		cityCodeMap.put( "UTTAR DINAJPUR","WB130");
		cityCodeMap.put( "UTTAR KANNAD","KA872");
		cityCodeMap.put( "UTTAR KASHI","UP289");
		cityCodeMap.put( "VADODARA","GJ550");
		cityCodeMap.put( "VAISHALI","BH089");
		cityCodeMap.put( "VALSAD","GJ556");
		cityCodeMap.put( "VARANASI","UP230");
		cityCodeMap.put( "VELLORE","TN904");
		cityCodeMap.put( "VIDISHA","MP735");
		cityCodeMap.put( "VILLUPURAM-RAMASAMY","TN940");
		cityCodeMap.put( "VIRUDHUNAGAR","TN926");
		cityCodeMap.put( "VISAKHAPATNAM","AP812");
		cityCodeMap.put( "VIZIANAGARAM","AP836");
		cityCodeMap.put( "WARANGAL","AP808");
		cityCodeMap.put( "WARDHA","MH642");
		cityCodeMap.put( "WAYANAD","KL988");
		cityCodeMap.put( "WEST GARO HILLS","ME024");
		cityCodeMap.put( "WEST GODAVARI","AP818");
		cityCodeMap.put( "WEST KAMENG","AR090");
		cityCodeMap.put( "WEST KHASI HILLS","ME027");
		cityCodeMap.put( "WEST NIMAR","MP722");
		cityCodeMap.put( "WEST SIANG","AR093");
		cityCodeMap.put( "WEST SIKKIM","SK197");
		cityCodeMap.put( "WEST TRIPURA","TP191");
		cityCodeMap.put( "WOKHA","NG147");
		cityCodeMap.put( "YAMUNANAGAR","HR341");
		cityCodeMap.put( "YAVATMAL","MH634");
		cityCodeMap.put( "ZUNHEBOTO","NG149");
		cityCodeMap.put( "ADILABAD","AP805");
		cityCodeMap.put( "AGRA","UP260");
		cityCodeMap.put( "AHMADNAGAR","MH622");
		cityCodeMap.put( "AHMEDABAD","GJ570");
		cityCodeMap.put( "AIZAWL","MZ030");
		cityCodeMap.put( "AJMER","RJ510");
		cityCodeMap.put( "AKOLA","MH646");
		cityCodeMap.put( "ALAPUZHA","KL966");
		cityCodeMap.put( "ALIGARH","UP264");
		cityCodeMap.put( "ALLAHABAD","UP220");
		cityCodeMap.put( "ALMORA","UP282");
		cityCodeMap.put( "ALWAR","RJ502");
		cityCodeMap.put( "AMBALA","HR340");
		cityCodeMap.put( "AMRAVATI","MH644");
		cityCodeMap.put( "AMRELI","GJ574");
		cityCodeMap.put( "AMRITSAR","PB310");
		cityCodeMap.put( "ANANTAPUR","AP829");
		cityCodeMap.put( "ANANTNAG","JK444");
		cityCodeMap.put( "ANGUL","OR169");
		cityCodeMap.put( "ANNA","TN939");
		cityCodeMap.put( "ARARIA","BH083");
		cityCodeMap.put( "AURANGABAD","BH058");
		cityCodeMap.put( "AURANGABAD","MH624");
		cityCodeMap.put( "AZAMGARH","UP226");
		cityCodeMap.put( "BADGAM","JK432");
		cityCodeMap.put( "BAHRAICH","UP244");
		cityCodeMap.put( "BALAGHAT","MP752");
		cityCodeMap.put( "BALANGIR","OR184");
		cityCodeMap.put( "BALESHWAR","OR172");
		cityCodeMap.put( "BALLIA","UP235");
		cityCodeMap.put( "BANAS KANTHA","GJ544");
		cityCodeMap.put( "BANDA","UP218");
		cityCodeMap.put( "BANGALORE","KA840");
		cityCodeMap.put( "BANGALORE RURAL","KA842");
		cityCodeMap.put( "BANKA","BH046");
		cityCodeMap.put( "BANKURA","WB115");
		cityCodeMap.put( "BANSWARA","RJ534");
		cityCodeMap.put( "BARA BANKI","UP202");
		cityCodeMap.put( "BARAMULLA","JK442");
		cityCodeMap.put( "BARAN","RJ512");
		cityCodeMap.put( "BARDDHAMAN","WB120");
		cityCodeMap.put( "BAREILLY","UP250");
		cityCodeMap.put( "BARGARH","OR173");
		cityCodeMap.put( "BARMER","RJ523");
		cityCodeMap.put( "BARPETA","AS021");
		cityCodeMap.put( "BASTAR","MP762");
		cityCodeMap.put( "BASTI","UP242");
		cityCodeMap.put( "BATHINDA","PB304");
		cityCodeMap.put( "BEGUSARAI","BH077");
		cityCodeMap.put( "BELGAUM","KA870");
		cityCodeMap.put( "BELLARY","KA858");
		cityCodeMap.put( "BETUL","MP717");
		cityCodeMap.put( "BHABUA","BH037");
		cityCodeMap.put( "BHADOHI","UP231");
		cityCodeMap.put( "BHADRAK","OR171");
		cityCodeMap.put( "BHAGALPUR","BH080");
		cityCodeMap.put( "BHANDARA","MH638");
		cityCodeMap.put( "BHARATPUR","RJ504");
		cityCodeMap.put( "BHARUCH","GJ553");
		cityCodeMap.put( "BHAVNAGAR","GJ572");
		cityCodeMap.put( "BHILWARA","RJ508");
		cityCodeMap.put( "BHIND","MP739");
		cityCodeMap.put( "BHIWANI","HR359");
		cityCodeMap.put( "BHOJPUR","BH052");
		cityCodeMap.put( "BHOPAL","MP764");
		cityCodeMap.put( "BID","MH628");
		cityCodeMap.put( "BIDAR","KA866");
		cityCodeMap.put( "BIJAPUR","KA868");
		cityCodeMap.put( "BIJNOR","UP276");
		cityCodeMap.put( "BIKANER","RJ520");
		cityCodeMap.put( "BILASPUR","HP462");
		cityCodeMap.put( "BILASPUR","MP754");
		cityCodeMap.put( "BIRBHUM","WB122");
		cityCodeMap.put( "BISHENPUR","MN155");
		cityCodeMap.put( "BOKARO","BH035");
		cityCodeMap.put( "BONGAIGAON","AS002");
		cityCodeMap.put( "BOUDH","OR187");
		cityCodeMap.put( "BUDAUN","UP267");
		cityCodeMap.put( "BULANDSHAHR","UP268");
		cityCodeMap.put( "BULDHANA","MH648");
		cityCodeMap.put( "BUNDI","RJ536");
		cityCodeMap.put( "BUXAR","BH045");
		cityCodeMap.put( "CACHAR","AS022");
		cityCodeMap.put( "CHAMBA","HP472");
		cityCodeMap.put( "CHAMOLI","UP284");
		cityCodeMap.put( "CHANDEL","MN157");
		cityCodeMap.put( "CHANDIGARH","CH390");
		cityCodeMap.put( "CHANDRAPUR","MH636");
		cityCodeMap.put( "CHATRA","BH061");
		cityCodeMap.put( "CHENNAI","TN900");
		cityCodeMap.put( "CHHATARPUR","MP744");
		cityCodeMap.put( "CHHIMTUIPUI","MZ034");
		cityCodeMap.put( "CHHINDWARA","MP715");
		cityCodeMap.put( "CHIDAMBARANAR","TN933");
		cityCodeMap.put( "CHIKMAGALUR","KA877");
		cityCodeMap.put( "CHITRADURGA","KA856");
		cityCodeMap.put( "CHITTAURGARH","RJ535");
		cityCodeMap.put( "CHITTOOR","AP826");
		cityCodeMap.put( "CHUNGLANG","AR400");
		cityCodeMap.put( "CHURACHANDPUR","MN152");
		cityCodeMap.put( "CHURU","RJ516");
		cityCodeMap.put( "COIMBATORE","TN920");
		cityCodeMap.put( "CUDDALORE","TN906");
		cityCodeMap.put( "CUDDAPH","AP828");
		cityCodeMap.put( "CUTTACK","OR170");
		cityCodeMap.put( "DADRA & NAGAR HAVELI","DN698");
		cityCodeMap.put( "DAKSHIN DINAJPUR","WB131");
		cityCodeMap.put( "DAKSHIN KANNAD","KA880");
		cityCodeMap.put( "DAMAN","DD694");
		cityCodeMap.put( "DAMOH","MP708");
		cityCodeMap.put( "DANGS","GJ563");
		cityCodeMap.put( "DARBHANGA","BH074");
		cityCodeMap.put( "DARJILING","WB132");
		cityCodeMap.put( "DARRANG","AS007");
		cityCodeMap.put( "DATIA","MP742");
		cityCodeMap.put( "DAUSA","RJ509");
		cityCodeMap.put( "DAVANGERE","KA857");
		cityCodeMap.put( "DEHRA DUN","UP287");
		cityCodeMap.put( "DELHI","DL290");
		cityCodeMap.put( "DEOGARH","OR188");
		cityCodeMap.put( "DEOGHAR","BH040");
		cityCodeMap.put( "DEORIA","UP236");
		cityCodeMap.put( "DEWAS","MP719");
		cityCodeMap.put( "DHALAI","TP193");
		cityCodeMap.put( "DHANBAD","BH082");
		cityCodeMap.put( "DHAR","MP724");
		cityCodeMap.put( "DHARMAPURI","TN916");
		cityCodeMap.put( "DHARWAD","KA860");
		cityCodeMap.put( "DHEMAJI","AS410");
		cityCodeMap.put( "DHENKANAL","OR178");
		cityCodeMap.put( "DHOLPUR","RJ505");
		cityCodeMap.put( "DHUBRI","AS019");
		cityCodeMap.put( "DHULE","MH654");
		cityCodeMap.put( "DIBANG VALLEY","AR097");
		cityCodeMap.put( "DIBRUGARH","AS009");
		cityCodeMap.put( "DIU","DD696");
		cityCodeMap.put( "DODA","JK456");
		cityCodeMap.put( "DUMKA","BH041");
		cityCodeMap.put( "DUNGARPUR","RJ533");
		cityCodeMap.put( "DURG","MP750");
		cityCodeMap.put( "EAST GARO HILLS","ME023");
		cityCodeMap.put( "EAST GODAVARI","AP810");
		cityCodeMap.put( "EAST KAMENG","AR091");
		cityCodeMap.put( "EAST KHASI HILLS","ME026");
		cityCodeMap.put( "EAST NIMAR","MP718");
		cityCodeMap.put( "EAST SIANG","AR094");
		cityCodeMap.put( "EAST SIKKIM","SK196");
		cityCodeMap.put( "ERNAKULAM","KL970");
		cityCodeMap.put( "ETAH","UP266");
		cityCodeMap.put( "ETAWAH","UP256");
		cityCodeMap.put( "FAIZABAD","UP224");
		cityCodeMap.put( "FARIDABAD","HR361");
		cityCodeMap.put( "FARIDKOT","PB336");
		cityCodeMap.put( "FARRUKHABAD","UP254");
		cityCodeMap.put( "FATEHGARH SAHIB","PB318");
		cityCodeMap.put( "FATEHPUR","UP208");
		cityCodeMap.put( "FEROZPUR","PB306");
		cityCodeMap.put( "FIROZABAD","UP203");
		cityCodeMap.put( "GADCHIROLI","MH660");
		cityCodeMap.put( "GAJAPATI","OR161");
		cityCodeMap.put( "GANDHINAGAR","GJ540");
		cityCodeMap.put( "GANGANAGAR","RJ518");
		cityCodeMap.put( "GANJAM","OR162");
		cityCodeMap.put( "GARHWA","BH038");
		cityCodeMap.put( "GARHWAL","UP285");
		cityCodeMap.put( "GAUTAM BUDDHA NAGAR","UP271");
		cityCodeMap.put( "GAYA","BH070");
		cityCodeMap.put( "GHAZIABAD","UP269");
		cityCodeMap.put( "GHAZIPUR","UP234");
		cityCodeMap.put( "GIRIDIH","BH056");
		cityCodeMap.put( "GOALPARA","AS003");
		cityCodeMap.put( "GODDA","BH042");
		cityCodeMap.put( "GOLAGHAT","AS013");
		cityCodeMap.put( "GONDA","UP243");
		cityCodeMap.put( "GOPALGANJ","BH063");
		cityCodeMap.put( "GORAKHPUR","UP240");
		cityCodeMap.put( "GREATER MUMBAI","MH600");
		cityCodeMap.put( "GULBARGA","KA864");
		cityCodeMap.put( "GUMLA","BH043");
		cityCodeMap.put( "GUNA","MP736");
		cityCodeMap.put( "GUNTUR","AP822");
		cityCodeMap.put( "GURDASPUR","PB312");
		cityCodeMap.put( "GURGAON","HR348");
		cityCodeMap.put( "GWALIOR","MP740");
		cityCodeMap.put( "HAILAKANDI","AS004");
		cityCodeMap.put( "HAMIRPUR","HP482");
		cityCodeMap.put( "HAMIRPUR","UP216");
		cityCodeMap.put( "HANUMANGARH","RJ517");
		cityCodeMap.put( "HARDOI","UP206");
		cityCodeMap.put( "HARIDWAR","UP277");
		cityCodeMap.put( "HASSAN","KA846");
		cityCodeMap.put( "HAZARIBAG","BH084");
		cityCodeMap.put( "HISAR","HR350");
		cityCodeMap.put( "HOSHANGABAD","MP716");
		cityCodeMap.put( "HOSHIARPUR","PB332");
		cityCodeMap.put( "HOWRAH","WB110");
		cityCodeMap.put( "HUGLI","WB107");
		cityCodeMap.put( "HYDERABAD","AP800");
		cityCodeMap.put( "IDUKKI","KL976");
		cityCodeMap.put( "IMPHAL","MN158");
		cityCodeMap.put( "INDORE","MP720");
		cityCodeMap.put( "JABALPUR","MP710");
		cityCodeMap.put( "JAGATSINGHPUR","OR175");
		cityCodeMap.put( "JAINTIA HILLS","ME025");
		cityCodeMap.put( "JAIPUR","OR177");
		cityCodeMap.put( "JAIPUR","RJ500");
		cityCodeMap.put( "JAISALMER","RJ522");
		cityCodeMap.put( "JALANDHAR","PB320");
		cityCodeMap.put( "JALAUN","UP212");
		cityCodeMap.put( "JALGAON","MH656");
		cityCodeMap.put( "JALNA","MH625");
		cityCodeMap.put( "JALOR","RJ525");
		cityCodeMap.put( "JALPAIGURI","WB134");
		cityCodeMap.put( "JAMMU","JK450");
		cityCodeMap.put( "JAMNAGAR","GJ582");
		cityCodeMap.put( "JAMUI","BH047");
		cityCodeMap.put( "JAUNPUR","UP228");
		cityCodeMap.put( "JEHANABAD","BH081");
		cityCodeMap.put( "JHABUA","MP726");
		cityCodeMap.put( "JHALAWAR","RJ539");
		cityCodeMap.put( "JHANSI","UP214");
		cityCodeMap.put( "JHARSUGUDA","OR189");
		cityCodeMap.put( "JHUNJHUNUN","RJ515");
		cityCodeMap.put( "JIND","HR353");
		cityCodeMap.put( "JODHPUR","RJ530");
		cityCodeMap.put( "JORHAT","AS011");
		cityCodeMap.put( "JUNAGADH","GJ576");
		cityCodeMap.put( "KACHCHH","GJ586");
		cityCodeMap.put( "KAITHAL","HR354");
		cityCodeMap.put( "KAKROJHAR","AS020");
		cityCodeMap.put( "KALAHANDI","OR168");
		cityCodeMap.put( "KAMRUP","AS001");
		cityCodeMap.put( "KANCHEEPURAM","TN903");
		cityCodeMap.put( "KANGRA","HP470");
		cityCodeMap.put( "KANNUR","KL985");
		cityCodeMap.put( "KANPUR CITY","UP210");
		cityCodeMap.put( "KANPUR DEHAT","UP211");
		cityCodeMap.put( "KANYAKUMARI","TN936");
		cityCodeMap.put( "KAPURTHALA","PB314");
		cityCodeMap.put( "KARAIKAL","PN992");
		cityCodeMap.put( "KARBI ANGLONG","AS016");
		cityCodeMap.put( "KARGIL","JK438");
		cityCodeMap.put( "KARIMGANJ","AS017");
		cityCodeMap.put( "KARIMNAGAR","AP807");
		cityCodeMap.put( "KARNAL","HR342");
		cityCodeMap.put( "KASARAGOD","KL989");
		cityCodeMap.put( "KATHUA","JK452");
		cityCodeMap.put( "KATIHAR","BH071");
		cityCodeMap.put( "KENDRAPARA","OR179");
		cityCodeMap.put( "KEONJHAR","OR176");
		cityCodeMap.put( "KHAGARIA","BH085");
		cityCodeMap.put( "KHAMMAM","AP809");
		cityCodeMap.put( "KHEDA","GJ566");
		cityCodeMap.put( "KHURDA","OR181");
		cityCodeMap.put( "KINNAUR","HP476");
		cityCodeMap.put( "KISHANGANJ","BH049");
		cityCodeMap.put( "KOCH BIHAR","WB136");
		cityCodeMap.put( "KODAGU","KA882");
		cityCodeMap.put( "KODERMA","BH073");
		cityCodeMap.put( "KOHIMA","NG140");
		cityCodeMap.put( "KOLAR","KA852");
		cityCodeMap.put( "KOLHAPUR","MH608");
		cityCodeMap.put( "KOLKATA","WB100");
		cityCodeMap.put( "KOLLAM","KL963");
		cityCodeMap.put( "KORAPUT","OR166");
		cityCodeMap.put( "KOTA","RJ537");
		cityCodeMap.put( "KOTTAYAM","KL968");
		cityCodeMap.put( "KOZHIKODE","KL980");
		cityCodeMap.put( "KRISHNA","AP820");
		cityCodeMap.put( "KULU","HP468");
		cityCodeMap.put( "KUPWARA","JK436");
		cityCodeMap.put( "KURNOOL","AP830");
		cityCodeMap.put( "KURUKSHETRA","HR355");
		cityCodeMap.put( "LADAKH","JK458");
		cityCodeMap.put( "LAHUL & SPITI","HP474");
		cityCodeMap.put( "LAKHIMPUR","AS010");
		cityCodeMap.put( "LAKHIMPUR KHERI","UP247");
		cityCodeMap.put( "LAKHISARAI","BH075");
		cityCodeMap.put( "LAKSHADWEEP","LK890");
		cityCodeMap.put( "LALITPUR","UP213");
		cityCodeMap.put( "LATUR","MH662");
		cityCodeMap.put( "LOHARDAGGA","BH044");
		cityCodeMap.put( "LOHIT","AR092");
		cityCodeMap.put( "LOWER SUBANSIRI","AR095");
		cityCodeMap.put( "LUCKNOW","UP200");
		cityCodeMap.put( "LUDHIANA","PB330");
		cityCodeMap.put( "LUNGLEI","MZ032");
		cityCodeMap.put( "MADHEPURA","BH068");
		cityCodeMap.put( "MADHUBANI","BH054");
		cityCodeMap.put( "MADURAI","TN930");
		cityCodeMap.put( "MAHARAJGANJ","UP225");
		cityCodeMap.put( "MAHBUBNAGAR","AP834");
		cityCodeMap.put( "MAHE","PN994");
		cityCodeMap.put( "MAHENDRAGARH","HR346");
		cityCodeMap.put( "MAHESAN","GJ542");
		cityCodeMap.put( "MAHOBA","UP217");
		cityCodeMap.put( "MAINPURI","UP258");
		cityCodeMap.put( "MALAPPURAM","KL983");
		cityCodeMap.put( "MALDAH","WB128");
		cityCodeMap.put( "MALKANGIRI","OR163");
		cityCodeMap.put( "MANDI","HP464");
		cityCodeMap.put( "MANDLA","MP712");
		cityCodeMap.put( "MANDSAUR","MP728");
		cityCodeMap.put( "MANDYA","KA843");
		cityCodeMap.put( "MANSA","PB316");
		cityCodeMap.put( "MATHURA","UP262");
		cityCodeMap.put( "MAU","UP253");
		cityCodeMap.put( "MAYURBHANJ","OR174");
		cityCodeMap.put( "MEDAK","AP802");
		cityCodeMap.put( "MEDINIPUR","WB112");
		cityCodeMap.put( "MEERUT","UP270");
		cityCodeMap.put( "MIRZAPUR","UP232");
		cityCodeMap.put( "MOKOKCHUNG","NG142");
		cityCodeMap.put( "MON","NG146");
		cityCodeMap.put( "MORADABAD","UP278");
		cityCodeMap.put( "MORENA","MP738");
		cityCodeMap.put( "MORIGAON","AS005");
		cityCodeMap.put( "MUNGER","BH072");
		cityCodeMap.put( "MURSHIDABAD","WB126");
		cityCodeMap.put( "MUZAFFARNAGAR","UP272");
		cityCodeMap.put( "MUZAFFARPUR","BH062");
		cityCodeMap.put( "MYSORE","KA850");
		cityCodeMap.put( "NADIA","WB124");
		cityCodeMap.put( "NAGAON","AS014");
		cityCodeMap.put( "NAGAPATTINAM_Q_E_M","TN909");
		cityCodeMap.put( "NAGAUR","RJ511");
		cityCodeMap.put( "NAGPUR","MH640");
		cityCodeMap.put( "NAINITAL","UP281");
		cityCodeMap.put( "NALANDA","BH051");
		cityCodeMap.put( "NALBARI","AS008");
		cityCodeMap.put( "NALGONDA","AP832");
		cityCodeMap.put( "NANDED","MH632");
		cityCodeMap.put( "NARASIMHAPUR","MP714");
		cityCodeMap.put( "NASIK","MH650");
		cityCodeMap.put( "NAWADA","BH059");
		cityCodeMap.put( "NAWAPARA","OR185");
		cityCodeMap.put( "NAYAGARH","OR183");
		cityCodeMap.put( "NELLORE","AP824");
		cityCodeMap.put( "NICOBAR","AN195");
		cityCodeMap.put( "NILGIRI","TN918");
		cityCodeMap.put( "NIZAMABAD","AP803");
		cityCodeMap.put( "NORTH 24 PARGANAS","WB101");
		cityCodeMap.put( "NORTH CACHAR HILLS","AS018");
		cityCodeMap.put( "NORTH SIKKIM","SK198");
		cityCodeMap.put( "NORTH TRIPURA","TP190");
		cityCodeMap.put( "NORTH-GOA","GO690");
		cityCodeMap.put( "NOWRANGPUR","OR165");
		cityCodeMap.put( "OSMANABAD","MH630");
		cityCodeMap.put( "P.T. THIRUMAGAN","TN922");
		cityCodeMap.put( "PADRAUNA","UP237");
		cityCodeMap.put( "PAKUR","BH069");
		cityCodeMap.put( "PALAKKAD","KL974");
		cityCodeMap.put( "PALAMU","BH086");
		cityCodeMap.put( "PALI","RJ528");
		cityCodeMap.put( "PANCH MAHALS","GJ548");
		cityCodeMap.put( "PANCHKULA","HR345");
		cityCodeMap.put( "PANIPAT","HR343");
		cityCodeMap.put( "PANNA","MP745");
		cityCodeMap.put( "PAPUMPARE","AR401");
		cityCodeMap.put( "PARBHANI","MH626");
		cityCodeMap.put( "PASCHIMI CHAMPARAN","BH064");
		cityCodeMap.put( "PASCHIMI SINGHBHUM","BH088");
		cityCodeMap.put( "PATHANAMTHITTA","KL978");
		cityCodeMap.put( "PATIALA","PB300");
		cityCodeMap.put( "PATNA","BH060");
		cityCodeMap.put( "PERAMBALUR","TN914");
		cityCodeMap.put( "PERIYAR","TN924");
		cityCodeMap.put( "PHEK","NG148");
		cityCodeMap.put( "PHULABANI","OR164");
		cityCodeMap.put( "PILIBHIT","UP249");
		cityCodeMap.put( "PITHORAGARH","UP283");
		cityCodeMap.put( "PONDICHERRY","PN990");
		cityCodeMap.put( "POONCH","JK446");
		cityCodeMap.put( "PRAKASAM","AP825");
		cityCodeMap.put( "PRATAPGARH","UP222");
		cityCodeMap.put( "PUDUKKOTTAI","TN938");
		cityCodeMap.put( "PULWAMA","JK434");
		cityCodeMap.put( "PUNE","MH620");
		cityCodeMap.put( "PURBI CHAMPARAN","BH053");
		cityCodeMap.put( "PURBI SINGHBHUM","BH036");
		cityCodeMap.put( "PURI","OR160");
		cityCodeMap.put( "PURNIA","BH078");
		cityCodeMap.put( "PURULIYA","WB117");
		cityCodeMap.put( "RAI BARELI","UP204");
		cityCodeMap.put( "RAICHUR","KA862");
		cityCodeMap.put( "RAIGAD","MH603");
		cityCodeMap.put( "RAIGARH","MP758");
		cityCodeMap.put( "RAIPUR","CG760");
		cityCodeMap.put( "RAIPUR","MP760");
		cityCodeMap.put( "RAISEN","MP702");
		cityCodeMap.put( "RAJGARH","MP734");
		cityCodeMap.put( "RAJKOT","GJ580");
		cityCodeMap.put( "RAJNANDGAON","MP766");
		cityCodeMap.put( "RAJOURI","JK448");
		cityCodeMap.put( "RAJSAMAND","RJ514");
		cityCodeMap.put( "RAMANATHAPURAM","TN932");
		cityCodeMap.put( "RAMPUR","UP280");
		cityCodeMap.put( "RANCHI","BH087");
		cityCodeMap.put( "RANCHI","JH087");
		cityCodeMap.put( "RANGAREDDY","AP838");
		cityCodeMap.put( "RATLAM","MP727");
		cityCodeMap.put( "RATNAGIRI","MH605");
		cityCodeMap.put( "RAYAGADA","OR167");
		cityCodeMap.put( "REWA","MP747");
		cityCodeMap.put( "REWARI","HR347");
		cityCodeMap.put( "RI BHOI","ME028");
		cityCodeMap.put( "ROHTAK","HR344");
		cityCodeMap.put( "ROHTAS","BH067");
		cityCodeMap.put( "RUPNAGAR","PB334");
		cityCodeMap.put( "SABAR KANTHA","GJ546");
		cityCodeMap.put( "SAGAR","MP705");
		cityCodeMap.put( "SAHARANPUR","UP274");
		cityCodeMap.put( "SAHARSA","BH076");
		cityCodeMap.put( "SAHEBGANJ","BH039");
		cityCodeMap.put( "SALEM","TN913");
		cityCodeMap.put( "SAMASTIPUR","BH055");
		cityCodeMap.put( "SAMBALPUR","OR182");
		cityCodeMap.put( "SAMBUVARAYAR","TN919");
		cityCodeMap.put( "SANGLI","MH612");
		cityCodeMap.put( "SANGRUR","PB302");
		cityCodeMap.put( "SARAN","BH066");
		cityCodeMap.put( "SATARA","MH615");
		cityCodeMap.put( "SATNA","MP746");
		cityCodeMap.put( "SAWAI MADHOPUR","RJ506");
		cityCodeMap.put( "SEHORE","MP700");
		cityCodeMap.put( "SENAPATI","MN150");
		cityCodeMap.put( "SEONI","MP713");
		cityCodeMap.put( "SHAHDOL","MP749");
		cityCodeMap.put( "SHAHJAHANPUR","UP252");
		cityCodeMap.put( "SHAJAPUR","MP732");
		cityCodeMap.put( "SHEIKHPURA","BH079");
		cityCodeMap.put( "SHEOHAR","BH065");
		cityCodeMap.put( "SHIMOGA","KA874");
		cityCodeMap.put( "SHIVPURI","MP737");
		cityCodeMap.put( "SIBSAGAR","AS012");
		cityCodeMap.put( "SIDHARTHANAGAR","UP257");
		cityCodeMap.put( "SIDHI","MP748");
		cityCodeMap.put( "SIKAR","RJ513");
		cityCodeMap.put( "SIMLA","HP460");
		cityCodeMap.put( "SINDHUDURG","MH607");
		cityCodeMap.put( "SIRMAUR","HP480");
		cityCodeMap.put( "SIROHI","RJ526");
		cityCodeMap.put( "SIRSA","HR352");
		cityCodeMap.put( "SITAMARHI","BH050");
		cityCodeMap.put( "SITAPUR","UP245");
		cityCodeMap.put( "SIWAN","BH057");
		cityCodeMap.put( "SOLAN","HP486");
		cityCodeMap.put( "SOLAPUR","MH610");
		cityCodeMap.put( "SONBHADRA","UP288");
		cityCodeMap.put( "SONEPUR","OR186");
		cityCodeMap.put( "SONIPAT","HR357");
		cityCodeMap.put( "SONITPUR","AS006");
		cityCodeMap.put( "SOUTH 24 PARGANAS","WB103");
		cityCodeMap.put( "SOUTH GARO HILLS","ME029");
		cityCodeMap.put( "SOUTH SIKKIM","SK199");
		cityCodeMap.put( "SOUTH TRIPURA","TP192");
		cityCodeMap.put( "SOUTH-GOA","GO692");
		cityCodeMap.put( "SRIKAKULAM","AP815");
		cityCodeMap.put( "SRINAGAR","JK440");
		cityCodeMap.put( "SULTANPUR","UP223");
		cityCodeMap.put( "SUNDARGARH","OR180");
		cityCodeMap.put( "SUPAUL","BH048");
		cityCodeMap.put( "SURAT","GJ560");
		cityCodeMap.put( "SURENDRANAGAR","GJ584");
		cityCodeMap.put( "SURGUJA","MP756");
		cityCodeMap.put( "T.KATTABOMMAN","TN934");
		cityCodeMap.put( "TAMENGLONG","MN156");
		cityCodeMap.put( "TAWANG","AR099");
		cityCodeMap.put( "TEHRI GARHWAL","UP286");
		cityCodeMap.put( "THANE","MH601");
		cityCodeMap.put( "THANJAVUR","TN908");
		cityCodeMap.put( "THENI","TN931");
		cityCodeMap.put( "THIRUVALLUR","TN902");
		cityCodeMap.put( "THIRUVANANTHAPURAM","KL960");
		cityCodeMap.put( "THOUBAL","MN153");
		cityCodeMap.put( "THRISSUR","KL972");
		cityCodeMap.put( "TIKAMGARH","MP743");
		cityCodeMap.put( "TINSUKIA","AS015");
		cityCodeMap.put( "TIRAP","AR098");
		cityCodeMap.put( "TIRUCHIRAPALLI","TN910");
		cityCodeMap.put( "TIRUPUR","TN912");
		cityCodeMap.put( "TONK","RJ507");
		cityCodeMap.put( "TUENSANG","NG144");
		cityCodeMap.put( "TUMKUR","KA854");
		cityCodeMap.put( "UDAIPUR","RJ531");
		cityCodeMap.put( "UDHAMPUR","JK454");
		cityCodeMap.put( "UJJAIN","MP730");
		cityCodeMap.put( "UKHRUL","MN154");
		cityCodeMap.put( "UNA","HP484");
		cityCodeMap.put( "UNNAO","UP205");
		cityCodeMap.put( "UPPER SUBANSIRI","AR096");
		cityCodeMap.put( "UTTAR DINAJPUR","WB130");
		cityCodeMap.put( "UTTAR KANNAD","KA872");
		cityCodeMap.put( "UTTAR KASHI","UP289");
		cityCodeMap.put( "VADODARA","GJ550");
		cityCodeMap.put( "VAISHALI","BH089");
		cityCodeMap.put( "VALSAD","GJ556");
		cityCodeMap.put( "VARANASI","UP230");
		cityCodeMap.put( "VELLORE","TN904");
		cityCodeMap.put( "VIDISHA","MP735");
		cityCodeMap.put( "VILLUPURAM-RAMASAMY","TN940");
		cityCodeMap.put( "VIRUDHUNAGAR","TN926");
		cityCodeMap.put( "VISAKHAPATNAM","AP812");
		cityCodeMap.put( "VIZIANAGARAM","AP836");
		cityCodeMap.put( "WARANGAL","AP808");
		cityCodeMap.put( "WARDHA","MH642");
		cityCodeMap.put( "WAYANAD","KL988");
		cityCodeMap.put( "WEST GARO HILLS","ME024");
		cityCodeMap.put( "WEST GODAVARI","AP818");
		cityCodeMap.put( "WEST KAMENG","AR090");
		cityCodeMap.put( "WEST KHASI HILLS","ME027");
		cityCodeMap.put( "WEST NIMAR","MP722");
		cityCodeMap.put( "WEST SIANG","AR093");
		cityCodeMap.put( "WEST SIKKIM","SK197");
		cityCodeMap.put( "WEST TRIPURA","TP191");
		cityCodeMap.put( "WOKHA","NG147");
		cityCodeMap.put( "YAMUNANAGAR","HR341");
		cityCodeMap.put( "YAVATMAL","MH634");
		cityCodeMap.put( "ZUNHEBOTO","NG149");
	}
	
	/**
	 * return singleton object of GensmartCardDataHelper.
	 * @return
	 */
	public static GensmartCardDataHelper getInstance() {
		if (gensmartCardDataHelper == null) {
			gensmartCardDataHelper = new GensmartCardDataHelper();
		}
		return gensmartCardDataHelper;
	}
	public Map<Integer,List<StudentTO>> ConvertBostoTos(List<Student> listOfStudents,GensmartCardDataForm genscDataForm) throws Exception {
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		Map<Integer,List<StudentTO>> studentMap = new HashMap<Integer, List<StudentTO>>();
		List<Integer> studentId= new ArrayList<Integer>();
//		List<Integer> studentWithoutPhotosOrRegNos= GensmartCardDataHandler.getInstance().getStudentsWithoutPhotosAndRegNos(genscDataForm);
		
//		int count=0;
		
		if(listOfStudents!=null && !listOfStudents.isEmpty()){
			Iterator<Student> itr = listOfStudents.iterator();
			while (itr.hasNext()) {
				Student student = (Student) itr.next();
//				System.out.println(student.getId());
//				if(student.getId()==30214){// remove this
				boolean isPhotoAvailable=false;
				Set<ApplnDoc> docSet=student.getAdmAppln().getApplnDocs();
				if(docSet!=null && !docSet.isEmpty()){
					Iterator<ApplnDoc> docitr=docSet.iterator();
					while (docitr.hasNext()) {
						ApplnDoc applnDoc = (ApplnDoc) docitr.next();
						if(applnDoc.getIsPhoto()){
							if(applnDoc.getDocument()!=null && applnDoc.getDocument().length!=0){
								isPhotoAvailable=true;
//								count++;
							}
						}
					}
				}
				if(!listOfDetainedStudents.contains(student.getId())){
					List<StudentTO> stlist= null;
					StudentTO stTo= new StudentTO();
					stTo.setPhotoAvaliable(isPhotoAvailable);
					if(studentMap.containsKey(student.getAdmAppln().getCourseBySelectedCourseId().getId())){
						stlist= studentMap.remove(student.getAdmAppln().getCourseBySelectedCourseId().getId());
					}else{
						stlist=new ArrayList<StudentTO>();
					}
					
					studentId.add(student.getId());
					stTo.setStudentId(student.getId());
					stTo.setRegisterNo( student.getRegisterNo()!=null?student.getRegisterNo():"");
					if(student.getBankAccNo()!=null && !student.getBankAccNo().trim().isEmpty())
						stTo.setBankAccNo(student.getBankAccNo());
					else
						stTo.setBankAccNo("NEW");
					
					stTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName()!=null?student.getAdmAppln().getPersonalData().getFirstName(): ""+
							student.getAdmAppln().getPersonalData().getMiddleName()!=null?" "+student.getAdmAppln().getPersonalData().getMiddleName(): ""
								+student.getAdmAppln().getPersonalData().getLastName()!=null?" "+student.getAdmAppln().getPersonalData().getLastName(): "");
					
					stTo.setDob(student.getAdmAppln().getPersonalData().getDateOfBirth()!=null?CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), GensmartCardDataHelper.SQL_DATEFORMAT,GensmartCardDataHelper.FROM_DATEFORMAT):"");
					
					stTo.setGender(student.getAdmAppln().getPersonalData().getGender()!=null?student.getAdmAppln().getPersonalData().getGender():"");
					stTo.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getBankName()!=null?student.getAdmAppln().getCourseBySelectedCourseId().getBankName():"");
					if(student.getAdmAppln().getCourseBySelectedCourseId().getBankIncludeSection()!=null
						&& student.getAdmAppln().getCourseBySelectedCourseId().getBankIncludeSection()==true && student.getClassSchemewise()!=null 
						&& student.getClassSchemewise().getClasses().getSectionName()!=null && !student.getClassSchemewise().getClasses().getSectionName().isEmpty() ){
						stTo.setCourseCode(Integer.toString(student.getAdmAppln().getCourseBySelectedCourseId().getId())
								+student.getClassSchemewise().getClasses().getSectionName()+student.getAdmAppln().getAdmittedThrough().getAdmittedThroughCode().charAt(0));
					}
					else{
						stTo.setCourseCode(Integer.toString(student.getAdmAppln().getCourseBySelectedCourseId().getId())+student.getAdmAppln().getAdmittedThrough().getAdmittedThroughCode().charAt(0));
					}
					stTo.setPrgmName(student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getName()!=null?student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getName():"");
					stTo.setPrgmCode(student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getCode()!=null?student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getCode():"");
					
					stTo.setBloodGrp(getBloodGrp(student.getAdmAppln().getPersonalData().getBloodGroup()));
					
					stTo.setFatherName(student.getAdmAppln().getPersonalData().getFatherName()!=null?student.getAdmAppln().getPersonalData().getFatherName():"");
					stTo.setMotherName(student.getAdmAppln().getPersonalData().getMotherName()!=null?student.getAdmAppln().getPersonalData().getMotherName():"");
					if(student.getAdmAppln().getPersonalData().getMobileNo2()!=null && !student.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
						stTo.setMobileNo1(student.getAdmAppln().getPersonalData().getMobileNo2());
					}
					else{
						stTo.setMobileNo1(student.getAdmAppln().getPersonalData().getPhNo1()!=null?student.getAdmAppln().getPersonalData().getPhNo1():""
								+student.getAdmAppln().getPersonalData().getPhNo2()!=null?student.getAdmAppln().getPersonalData().getPhNo2():""
									+student.getAdmAppln().getPersonalData().getPhNo3()!=null?student.getAdmAppln().getPersonalData().getPhNo3():"");
					}
					stTo.setEmail(student.getAdmAppln().getPersonalData().getEmail()!=null?student.getAdmAppln().getPersonalData().getEmail():"");
					stTo.setPermAddress1(student.getAdmAppln().getPersonalData().getPermanentAddressLine1()!=null?student.getAdmAppln().getPersonalData().getPermanentAddressLine1():"");
					stTo.setPermAddress2(student.getAdmAppln().getPersonalData().getPermanentAddressLine2()!=null?student.getAdmAppln().getPersonalData().getPermanentAddressLine2():"");
					
					if(cityCodeMap.containsKey(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId().toUpperCase())){
						
						stTo.setPermanantCityCode(cityCodeMap.get(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId().toUpperCase()));
					}
					else
						stTo.setPermanantCityCode("ZZZZ");
					
					stTo.setPermanantBankStateId(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!=null?
							student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getBankStateId():"ZZ");
					
					
					stTo.setPermAddressCountry(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!=null?
							student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName():student.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers());
					stTo.setPermAddressZip(student.getAdmAppln().getPersonalData().getPermanentAddressZipCode()!=null?student.getAdmAppln().getPersonalData().getPermanentAddressZipCode():"");
					stTo.setCurrentAddress1(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null?student.getAdmAppln().getPersonalData().getCurrentAddressLine1():"");
					stTo.setCurrentAddress2(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null?student.getAdmAppln().getPersonalData().getCurrentAddressLine2():"");
					stTo.setCurrentAddressCity(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!=null?student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId():"");
					
					if(cityCodeMap.containsKey(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId().toUpperCase())){
						
						stTo.setCurrentCityCode(cityCodeMap.get(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId().toUpperCase()));
					}
					else
						stTo.setCurrentCityCode("ZZZZ");
					
					stTo.setCurrentBankStateId(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!=null?
							student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getBankStateId():"ZZ");
					
					
					stTo.setCurrentAddressState(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null?
							student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName().toUpperCase():
								(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null?student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().toUpperCase():""));
					stTo.setCurrentAddressCountry(student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!=null?
							student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName():student.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers());
					stTo.setCurrentAddressZip(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null?student.getAdmAppln().getPersonalData().getCurrentAddressZipCode():"");					
					stTo.setAppliedYear(student.getAdmAppln().getAppliedYear());
					stTo.setClassName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getName():"");
					stTo.setSectionName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getSectionName():"");
					int courseComYear=0;
					if(student.getAdmAppln().getCourseBySelectedCourseId().getCurriculumSchemes()!=null){
						Iterator<CurriculumScheme> currSchemeItr=student.getAdmAppln().getCourseBySelectedCourseId().getCurriculumSchemes().iterator();
						while (currSchemeItr.hasNext()) {
							CurriculumScheme curriculumScheme = (CurriculumScheme) currSchemeItr.next();
							if(curriculumScheme.getYear().intValue()==student.getAdmAppln().getAppliedYear().intValue()){
							if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("year")){
								courseComYear= student.getAdmAppln().getAppliedYear()+(curriculumScheme.getNoScheme());
								}
							else if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("semester")){
							courseComYear= student.getAdmAppln().getAppliedYear()+((curriculumScheme.getNoScheme())/2);
							}
							else if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("Trimester")){
								courseComYear= student.getAdmAppln().getAppliedYear()+((curriculumScheme.getNoScheme())/3);
							}
						}
						}
					}
					stTo.setCourseCompletion("31-05-"+String.valueOf(courseComYear));
					stTo.setPbankCCode(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!=null?student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getBankCCode():"");
					stTo.setSCDataGenerated(student.getIsSCDataGenerated());
					
					stlist.add(stTo);
					studentMap.put(student.getAdmAppln().getCourseBySelectedCourseId().getId(), stlist);
			}
//			}// remove this
		}
		}
		
		genscDataForm.setStudentIds(studentId);
//		genscDataForm.setStudentCount(count);
		return studentMap;
	}
	
	/**
	 * converting the bloodgroup string according to the requirement
	 * @param bg
	 * @return
	 */
	private String getBloodGrp(String bg){
		String bloodgrp=null;
		if(bg.equalsIgnoreCase("A+ve"))
			bloodgrp="A+";
		else if(bg.equalsIgnoreCase("B+ve"))
			bloodgrp="B+";
		else if(bg.equalsIgnoreCase("A-ve"))
			bloodgrp="A-";
		else if(bg.equalsIgnoreCase("B-ve"))
			bloodgrp="B-";
		else if(bg.equalsIgnoreCase("AB+ve"))
			bloodgrp="AB+";
		else if(bg.equalsIgnoreCase("AB-ve"))
			bloodgrp="AB-";
		else if(bg.equalsIgnoreCase("O+ve"))
			bloodgrp="O+";
		else if(bg.equalsIgnoreCase("O-ve"))
			bloodgrp="O-";
		else if(bg.equalsIgnoreCase("Not known"))
			bloodgrp="N";
		else
			bloodgrp="";
		return bloodgrp;
		
	}
	
	/**
	 * method to set employee info to map for smart card data print
	 * @param listOfEmployees
	 * @param genscDataForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, List<EmployeeTO>> ConvertEmpBostoTos(List<Employee> listOfEmployees, GensmartCardDataForm genscDataForm) throws Exception{
		Map<Integer,List<EmployeeTO>> employeeMap = new HashMap<Integer, List<EmployeeTO>>();
		List<Integer> employeeId= new ArrayList<Integer>();
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		String query="select e.employee.id from EmpImages e where (e.empPhoto <> null or trim(e.empPhoto) <> '') and e.employee.isActive=1 and e.employee.active=1";
		List<Integer> empImages=transaction.getDataForQuery(query);
//		List<Integer> studentWithoutPhotosOrRegNos= GensmartCardDataHandler.getInstance().getStudentsWithoutPhotosAndRegNos(genscDataForm);
		
//		int count=0;
		
		if(listOfEmployees!=null && !listOfEmployees.isEmpty()){
			Iterator<Employee> itr = listOfEmployees.iterator();
			
			while (itr.hasNext()) {
				Employee employee = (Employee) itr.next();
				if(employee.getDepartment()!=null){
//				System.out.println(student.getId());
//				if(student.getId()==30214){// remove this
				boolean isPhotoAvailable=false;
				if(empImages.contains(employee.getId())){
						isPhotoAvailable=true;
//								count++;
				}
				
				
					List<EmployeeTO> emplist= null;
					EmployeeTO empTo= new EmployeeTO();
					empTo.setPhotoAvailable(isPhotoAvailable);
					if(employeeMap.containsKey(employee.getDepartment().getId())){
						emplist= employeeMap.remove(employee.getDepartment().getId());
					}else{
						emplist=new ArrayList<EmployeeTO>();
					}
					
					employeeId.add(employee.getId());
					empTo.setId(employee.getId());
					empTo.setFingerPrintId(employee.getFingerPrintId()!=null?employee.getFingerPrintId():"");
					if(employee.getBankAccNo()!=null && !employee.getBankAccNo().trim().isEmpty())
						empTo.setBankAccNo(employee.getBankAccNo());
					else
						empTo.setBankAccNo("NEW");
					
					empTo.setName(employee.getFirstName()!=null?employee.getFirstName(): ""+employee.getMiddleName()!=null?" "+employee.getMiddleName(): ""
								+employee.getLastName()!=null?" "+employee.getLastName(): "");
					
					empTo.setDob(employee.getDob()!=null?CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(employee.getDob()), GensmartCardDataHelper.SQL_DATEFORMAT,GensmartCardDataHelper.FROM_DATEFORMAT):"");
					
					empTo.setGender(employee.getGender()!=null?String.valueOf(employee.getGender().charAt(0)):"");
					if(employee.getDepartment()!=null){
					empTo.setDepartmentName(employee.getDepartment().getName()!=null?employee.getDepartment().getName().toUpperCase():"");
					}
					if(employee.getDepartment()!=null){
						empTo.setDepartmentCode(employee.getDepartment().getCode()!=null?employee.getDepartment().getCode().toUpperCase():"");
					}
					
					if(employee.getDesignation()!=null){
						empTo.setDesignationName(employee.getDesignation().getName()!=null?employee.getDesignation().getName().toUpperCase():"");
					}
		/**			if(student.getAdmAppln().getCourseBySelectedCourseId().getBankIncludeSection()!=null && student.getAdmAppln().getCourseBySelectedCourseId().getBankIncludeSection()==1){
						stTo.setCourseCode(student.getAdmAppln().getCourseBySelectedCourseId().getId()
								+student.getClassSchemewise().getClasses().getSectionName()+student.getAdmAppln().getAdmittedThrough().getAdmittedThroughCode().charAt(0));
					}
					else{
						stTo.setCourseCode(Integer.toString(student.getAdmAppln().getCourseBySelectedCourseId().getId())+student.getAdmAppln().getAdmittedThrough().getAdmittedThroughCode().charAt(0));
					}
					stTo.setPrgmName(student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getName()!=null?student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getName():"");
					stTo.setPrgmCode(student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getCode()!=null?student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getCode():"");
					*/
					empTo.setBloodGroup(employee.getBloodGroup()!=null?getBloodGrp(employee.getBloodGroup()):"");
					
					empTo.setFatherName(employee.getFatherName()!=null?employee.getFatherName():"");
					empTo.setMotherName(employee.getMotherName()!=null?employee.getMotherName():"");
					if(employee.getCurrentAddressMobile1()!=null && !employee.getCurrentAddressMobile1().isEmpty()){
						empTo.setMobile(employee.getCurrentAddressMobile1());
					}
					else{
						empTo.setPhone1(employee.getCurrentAddressHomeTelephone1()!=null?employee.getCurrentAddressHomeTelephone1():""
								+employee.getCurrentAddressHomeTelephone2()!=null?employee.getCurrentAddressHomeTelephone2():""
								+employee.getCurrentAddressHomeTelephone3()!=null?employee.getCurrentAddressHomeTelephone3():"");
					}
					empTo.setEmail(employee.getEmail()!=null?employee.getEmail():"");
					empTo.setPermanentAddressLine1(employee.getPermanentAddressLine1()!=null?employee.getPermanentAddressLine1():"");
					empTo.setPermanentAddressLine2(employee.getPermanentAddressLine2()!=null?employee.getPermanentAddressLine2():"");
					if(employee.getPermanentAddressCity()!=null){
					if(cityCodeMap.containsKey(employee.getPermanentAddressCity().toUpperCase())){
						empTo.setPermanentAddressCity(cityCodeMap.get(employee.getPermanentAddressCity().toUpperCase()));
					}
					else
						empTo.setPermanentAddressCity("ZZZZ");
					}
					empTo.setPermanantBankStateId(employee.getStateByPermanentAddressStateId()!=null?
							employee.getStateByPermanentAddressStateId().getBankStateId():"ZZ");
					
					
					empTo.setPermanentAddressCountry(employee.getCountryByPermanentAddressCountryId()!=null?
							employee.getCountryByPermanentAddressCountryId().getName():"");
					empTo.setPermanentAddressZip(employee.getPermanentAddressZip()!=null?employee.getPermanentAddressZip():"");
				    empTo.setCommunicationAddressLine1(employee.getCommunicationAddressLine1()!=null?employee.getCommunicationAddressLine1().toUpperCase():"");
					empTo.setCommunicationAddressLine2(employee.getCommunicationAddressLine2()!=null?employee.getCommunicationAddressLine2().toUpperCase():"");
					empTo.setCommunicationAddressCity(employee.getCommunicationAddressCity()!=null?employee.getCommunicationAddressCity().toUpperCase():"");
					
					if(employee.getCommunicationAddressCity()!=null){
					if(cityCodeMap.containsKey(employee.getCommunicationAddressCity().toUpperCase())){
						
						empTo.setCurrentCityCode(cityCodeMap.get(employee.getCommunicationAddressCity().toUpperCase()));
					}
					else
						empTo.setCurrentCityCode("ZZZZ");
					}
					empTo.setCurrentBankStateId(employee.getStateByCommunicationAddressStateId()!=null?
							employee.getStateByCommunicationAddressStateId().getBankStateId():"ZZ");
					
					
					empTo.setCommunicationStateName(employee.getStateByCommunicationAddressStateId()!=null?
							employee.getStateByCommunicationAddressStateId().getName().toUpperCase():
								(employee.getCommunicationAddressStateOthers()!=null?employee.getCommunicationAddressStateOthers().toUpperCase():""));
					empTo.setCommunicationCountryName(employee.getCountryByCommunicationAddressCountryId()!=null?
							employee.getCountryByCommunicationAddressCountryId().getName():"");
					empTo.setCommunicationAddressZip(employee.getCommunicationAddressZip()!=null?employee.getCommunicationAddressZip():"");					
			//		stTo.setAppliedYear(student.getAdmAppln().getAppliedYear());
			//		stTo.setClassName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getName():"");
			//		stTo.setSectionName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getSectionName():"");
			//		int courseComYear=0;
			//		if(student.getClassSchemewise()!=null){
			//		if(student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName().equalsIgnoreCase("year")){
			//			courseComYear= student.getAdmAppln().getAppliedYear()+(student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getNoScheme());
			//			}
			//		else if(student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName().equalsIgnoreCase("semester")){
			//		courseComYear= student.getAdmAppln().getAppliedYear()+((student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getNoScheme())/2);
			//		}
				//	else if(student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName().equalsIgnoreCase("semester")){
				//		courseComYear= student.getAdmAppln().getAppliedYear()+((student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getNoScheme())/3);
				//		}
				//	}
				//	stTo.setCourseCompletion("31-05-"+String.valueOf(courseComYear));
					empTo.setPbankCCode(employee.getCountryByPermanentAddressCountryId()!=null?employee.getCountryByPermanentAddressCountryId().getBankCCode():"");
			//		empTo.setEmpSCDataGenerated(student.getIsSCDataGenerated());
					
					emplist.add(empTo);
					employeeMap.put(employee.getDepartment().getId(), emplist);
					
//			}// remove this
		}
		}
		}
		
		genscDataForm.setEmployeeIds(employeeId);
//		genscDataForm.setStudentCount(count);
		return employeeMap;
	}
}
