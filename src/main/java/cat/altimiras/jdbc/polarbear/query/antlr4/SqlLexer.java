// Generated from Sql.g4 by ANTLR 4.7.2
package cat.altimiras.jdbc.polarbear.query.antlr4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SqlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SCOL=1, DOT=2, TWO_DOTS=3, OPEN_PAR=4, CLOSE_PAR=5, COMMA=6, ASSIGN=7, 
		STAR=8, PLUS=9, MINUS=10, TILDE=11, PIPE2=12, DIV=13, MOD=14, LT2=15, 
		GT2=16, AMP=17, PIPE=18, LT=19, LT_EQ=20, GT=21, GT_EQ=22, EQ=23, NOT_EQ1=24, 
		NOT_EQ2=25, K_AND=26, K_OR=27, K_NOT=28, K_FROM=29, K_SELECT=30, K_WHERE=31, 
		K_AS=32, K_IN=33, K_LIKE=34, K_REGEXP=35, IDENTIFIER=36, NUMERIC_LITERAL=37, 
		DATE=38, TIME=39, DATE_TIME=40, FOUR_DIGITS=41, TWO_DIGITS=42, QUOTE=43, 
		BIND_PARAMETER=44, STRING_LITERAL=45, SPACES=46, UNEXPECTED_CHAR=47;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SCOL", "DOT", "TWO_DOTS", "OPEN_PAR", "CLOSE_PAR", "COMMA", "ASSIGN", 
			"STAR", "PLUS", "MINUS", "TILDE", "PIPE2", "DIV", "MOD", "LT2", "GT2", 
			"AMP", "PIPE", "LT", "LT_EQ", "GT", "GT_EQ", "EQ", "NOT_EQ1", "NOT_EQ2", 
			"K_AND", "K_OR", "K_NOT", "K_FROM", "K_SELECT", "K_WHERE", "K_AS", "K_IN", 
			"K_LIKE", "K_REGEXP", "IDENTIFIER", "NUMERIC_LITERAL", "DATE", "TIME", 
			"DATE_TIME", "FOUR_DIGITS", "TWO_DIGITS", "QUOTE", "BIND_PARAMETER", 
			"STRING_LITERAL", "SPACES", "UNEXPECTED_CHAR", "DIGIT", "A", "B", "C", 
			"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", 
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'.'", "':'", "'('", "')'", "','", "'='", "'*'", "'+'", 
			"'-'", "'~'", "'||'", "'/'", "'%'", "'<<'", "'>>'", "'&'", "'|'", "'<'", 
			"'<='", "'>'", "'>='", "'=='", "'!='", "'<>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SCOL", "DOT", "TWO_DOTS", "OPEN_PAR", "CLOSE_PAR", "COMMA", "ASSIGN", 
			"STAR", "PLUS", "MINUS", "TILDE", "PIPE2", "DIV", "MOD", "LT2", "GT2", 
			"AMP", "PIPE", "LT", "LT_EQ", "GT", "GT_EQ", "EQ", "NOT_EQ1", "NOT_EQ2", 
			"K_AND", "K_OR", "K_NOT", "K_FROM", "K_SELECT", "K_WHERE", "K_AS", "K_IN", 
			"K_LIKE", "K_REGEXP", "IDENTIFIER", "NUMERIC_LITERAL", "DATE", "TIME", 
			"DATE_TIME", "FOUR_DIGITS", "TWO_DIGITS", "QUOTE", "BIND_PARAMETER", 
			"STRING_LITERAL", "SPACES", "UNEXPECTED_CHAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public SqlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Sql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\61\u01cb\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4"+
		"I\tI\4J\tJ\4K\tK\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3"+
		"\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3"+
		"\25\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3"+
		"\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36\3\36\3"+
		"\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3!"+
		"\3!\3!\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\7%"+
		"\u0105\n%\f%\16%\u0108\13%\3%\3%\3%\3%\3%\7%\u010f\n%\f%\16%\u0112\13"+
		"%\3%\3%\3%\7%\u0117\n%\f%\16%\u011a\13%\3%\3%\3%\7%\u011f\n%\f%\16%\u0122"+
		"\13%\5%\u0124\n%\3&\6&\u0127\n&\r&\16&\u0128\3&\3&\7&\u012d\n&\f&\16&"+
		"\u0130\13&\5&\u0132\n&\3&\3&\5&\u0136\n&\3&\6&\u0139\n&\r&\16&\u013a\5"+
		"&\u013d\n&\3&\3&\6&\u0141\n&\r&\16&\u0142\3&\3&\5&\u0147\n&\3&\6&\u014a"+
		"\n&\r&\16&\u014b\5&\u014e\n&\5&\u0150\n&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3(\3(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3"+
		"*\3*\3*\3*\3*\3+\3+\3+\3,\3,\3-\3-\7-\u017c\n-\f-\16-\u017f\13-\3-\3-"+
		"\5-\u0183\n-\3.\3.\3.\3.\7.\u0189\n.\f.\16.\u018c\13.\3.\3.\3/\3/\3/\3"+
		"/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66"+
		"\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\3A\3"+
		"A\3B\3B\3C\3C\3D\3D\3E\3E\3F\3F\3G\3G\3H\3H\3I\3I\3J\3J\3K\3K\2\2L\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\2c\2e\2g\2i\2k\2m\2o\2q\2s\2u"+
		"\2w\2y\2{\2}\2\177\2\u0081\2\u0083\2\u0085\2\u0087\2\u0089\2\u008b\2\u008d"+
		"\2\u008f\2\u0091\2\u0093\2\u0095\2\3\2\'\3\2$$\3\2bb\3\2__\5\2C\\aac|"+
		"\6\2\62;C\\aac|\4\2--//\4\2$$))\5\2&&<<BB\3\2))\5\2\13\r\17\17\"\"\3\2"+
		"\62;\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4"+
		"\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSs"+
		"s\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2"+
		"\\\\||\2\u01c7\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3"+
		"\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2"+
		"\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\2"+
		"9\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3"+
		"\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2"+
		"\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2"+
		"_\3\2\2\2\3\u0097\3\2\2\2\5\u0099\3\2\2\2\7\u009b\3\2\2\2\t\u009d\3\2"+
		"\2\2\13\u009f\3\2\2\2\r\u00a1\3\2\2\2\17\u00a3\3\2\2\2\21\u00a5\3\2\2"+
		"\2\23\u00a7\3\2\2\2\25\u00a9\3\2\2\2\27\u00ab\3\2\2\2\31\u00ad\3\2\2\2"+
		"\33\u00b0\3\2\2\2\35\u00b2\3\2\2\2\37\u00b4\3\2\2\2!\u00b7\3\2\2\2#\u00ba"+
		"\3\2\2\2%\u00bc\3\2\2\2\'\u00be\3\2\2\2)\u00c0\3\2\2\2+\u00c3\3\2\2\2"+
		"-\u00c5\3\2\2\2/\u00c8\3\2\2\2\61\u00cb\3\2\2\2\63\u00ce\3\2\2\2\65\u00d1"+
		"\3\2\2\2\67\u00d5\3\2\2\29\u00d8\3\2\2\2;\u00dc\3\2\2\2=\u00e1\3\2\2\2"+
		"?\u00e8\3\2\2\2A\u00ee\3\2\2\2C\u00f1\3\2\2\2E\u00f4\3\2\2\2G\u00f9\3"+
		"\2\2\2I\u0123\3\2\2\2K\u014f\3\2\2\2M\u0151\3\2\2\2O\u0159\3\2\2\2Q\u0161"+
		"\3\2\2\2S\u016f\3\2\2\2U\u0174\3\2\2\2W\u0177\3\2\2\2Y\u0182\3\2\2\2["+
		"\u0184\3\2\2\2]\u018f\3\2\2\2_\u0193\3\2\2\2a\u0195\3\2\2\2c\u0197\3\2"+
		"\2\2e\u0199\3\2\2\2g\u019b\3\2\2\2i\u019d\3\2\2\2k\u019f\3\2\2\2m\u01a1"+
		"\3\2\2\2o\u01a3\3\2\2\2q\u01a5\3\2\2\2s\u01a7\3\2\2\2u\u01a9\3\2\2\2w"+
		"\u01ab\3\2\2\2y\u01ad\3\2\2\2{\u01af\3\2\2\2}\u01b1\3\2\2\2\177\u01b3"+
		"\3\2\2\2\u0081\u01b5\3\2\2\2\u0083\u01b7\3\2\2\2\u0085\u01b9\3\2\2\2\u0087"+
		"\u01bb\3\2\2\2\u0089\u01bd\3\2\2\2\u008b\u01bf\3\2\2\2\u008d\u01c1\3\2"+
		"\2\2\u008f\u01c3\3\2\2\2\u0091\u01c5\3\2\2\2\u0093\u01c7\3\2\2\2\u0095"+
		"\u01c9\3\2\2\2\u0097\u0098\7=\2\2\u0098\4\3\2\2\2\u0099\u009a\7\60\2\2"+
		"\u009a\6\3\2\2\2\u009b\u009c\7<\2\2\u009c\b\3\2\2\2\u009d\u009e\7*\2\2"+
		"\u009e\n\3\2\2\2\u009f\u00a0\7+\2\2\u00a0\f\3\2\2\2\u00a1\u00a2\7.\2\2"+
		"\u00a2\16\3\2\2\2\u00a3\u00a4\7?\2\2\u00a4\20\3\2\2\2\u00a5\u00a6\7,\2"+
		"\2\u00a6\22\3\2\2\2\u00a7\u00a8\7-\2\2\u00a8\24\3\2\2\2\u00a9\u00aa\7"+
		"/\2\2\u00aa\26\3\2\2\2\u00ab\u00ac\7\u0080\2\2\u00ac\30\3\2\2\2\u00ad"+
		"\u00ae\7~\2\2\u00ae\u00af\7~\2\2\u00af\32\3\2\2\2\u00b0\u00b1\7\61\2\2"+
		"\u00b1\34\3\2\2\2\u00b2\u00b3\7\'\2\2\u00b3\36\3\2\2\2\u00b4\u00b5\7>"+
		"\2\2\u00b5\u00b6\7>\2\2\u00b6 \3\2\2\2\u00b7\u00b8\7@\2\2\u00b8\u00b9"+
		"\7@\2\2\u00b9\"\3\2\2\2\u00ba\u00bb\7(\2\2\u00bb$\3\2\2\2\u00bc\u00bd"+
		"\7~\2\2\u00bd&\3\2\2\2\u00be\u00bf\7>\2\2\u00bf(\3\2\2\2\u00c0\u00c1\7"+
		">\2\2\u00c1\u00c2\7?\2\2\u00c2*\3\2\2\2\u00c3\u00c4\7@\2\2\u00c4,\3\2"+
		"\2\2\u00c5\u00c6\7@\2\2\u00c6\u00c7\7?\2\2\u00c7.\3\2\2\2\u00c8\u00c9"+
		"\7?\2\2\u00c9\u00ca\7?\2\2\u00ca\60\3\2\2\2\u00cb\u00cc\7#\2\2\u00cc\u00cd"+
		"\7?\2\2\u00cd\62\3\2\2\2\u00ce\u00cf\7>\2\2\u00cf\u00d0\7@\2\2\u00d0\64"+
		"\3\2\2\2\u00d1\u00d2\5c\62\2\u00d2\u00d3\5}?\2\u00d3\u00d4\5i\65\2\u00d4"+
		"\66\3\2\2\2\u00d5\u00d6\5\177@\2\u00d6\u00d7\5\u0085C\2\u00d78\3\2\2\2"+
		"\u00d8\u00d9\5}?\2\u00d9\u00da\5\177@\2\u00da\u00db\5\u0089E\2\u00db:"+
		"\3\2\2\2\u00dc\u00dd\5m\67\2\u00dd\u00de\5\u0085C\2\u00de\u00df\5\177"+
		"@\2\u00df\u00e0\5{>\2\u00e0<\3\2\2\2\u00e1\u00e2\5\u0087D\2\u00e2\u00e3"+
		"\5k\66\2\u00e3\u00e4\5y=\2\u00e4\u00e5\5k\66\2\u00e5\u00e6\5g\64\2\u00e6"+
		"\u00e7\5\u0089E\2\u00e7>\3\2\2\2\u00e8\u00e9\5\u008fH\2\u00e9\u00ea\5"+
		"q9\2\u00ea\u00eb\5k\66\2\u00eb\u00ec\5\u0085C\2\u00ec\u00ed\5k\66\2\u00ed"+
		"@\3\2\2\2\u00ee\u00ef\5c\62\2\u00ef\u00f0\5\u0087D\2\u00f0B\3\2\2\2\u00f1"+
		"\u00f2\5s:\2\u00f2\u00f3\5}?\2\u00f3D\3\2\2\2\u00f4\u00f5\5y=\2\u00f5"+
		"\u00f6\5s:\2\u00f6\u00f7\5w<\2\u00f7\u00f8\5k\66\2\u00f8F\3\2\2\2\u00f9"+
		"\u00fa\5\u0085C\2\u00fa\u00fb\5k\66\2\u00fb\u00fc\5o8\2\u00fc\u00fd\5"+
		"k\66\2\u00fd\u00fe\5\u0091I\2\u00fe\u00ff\5\u0081A\2\u00ffH\3\2\2\2\u0100"+
		"\u0106\7$\2\2\u0101\u0105\n\2\2\2\u0102\u0103\7$\2\2\u0103\u0105\7$\2"+
		"\2\u0104\u0101\3\2\2\2\u0104\u0102\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104"+
		"\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u0106\3\2\2\2\u0109"+
		"\u0124\7$\2\2\u010a\u0110\7b\2\2\u010b\u010f\n\3\2\2\u010c\u010d\7b\2"+
		"\2\u010d\u010f\7b\2\2\u010e\u010b\3\2\2\2\u010e\u010c\3\2\2\2\u010f\u0112"+
		"\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0113\3\2\2\2\u0112"+
		"\u0110\3\2\2\2\u0113\u0124\7b\2\2\u0114\u0118\7]\2\2\u0115\u0117\n\4\2"+
		"\2\u0116\u0115\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u0116\3\2\2\2\u0118\u0119"+
		"\3\2\2\2\u0119\u011b\3\2\2\2\u011a\u0118\3\2\2\2\u011b\u0124\7_\2\2\u011c"+
		"\u0120\t\5\2\2\u011d\u011f\t\6\2\2\u011e\u011d\3\2\2\2\u011f\u0122\3\2"+
		"\2\2\u0120\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0124\3\2\2\2\u0122"+
		"\u0120\3\2\2\2\u0123\u0100\3\2\2\2\u0123\u010a\3\2\2\2\u0123\u0114\3\2"+
		"\2\2\u0123\u011c\3\2\2\2\u0124J\3\2\2\2\u0125\u0127\5a\61\2\u0126\u0125"+
		"\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0126\3\2\2\2\u0128\u0129\3\2\2\2\u0129"+
		"\u0131\3\2\2\2\u012a\u012e\7\60\2\2\u012b\u012d\5a\61\2\u012c\u012b\3"+
		"\2\2\2\u012d\u0130\3\2\2\2\u012e\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f"+
		"\u0132\3\2\2\2\u0130\u012e\3\2\2\2\u0131\u012a\3\2\2\2\u0131\u0132\3\2"+
		"\2\2\u0132\u013c\3\2\2\2\u0133\u0135\5k\66\2\u0134\u0136\t\7\2\2\u0135"+
		"\u0134\3\2\2\2\u0135\u0136\3\2\2\2\u0136\u0138\3\2\2\2\u0137\u0139\5a"+
		"\61\2\u0138\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a\u0138\3\2\2\2\u013a"+
		"\u013b\3\2\2\2\u013b\u013d\3\2\2\2\u013c\u0133\3\2\2\2\u013c\u013d\3\2"+
		"\2\2\u013d\u0150\3\2\2\2\u013e\u0140\7\60\2\2\u013f\u0141\5a\61\2\u0140"+
		"\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143\3\2"+
		"\2\2\u0143\u014d\3\2\2\2\u0144\u0146\5k\66\2\u0145\u0147\t\7\2\2\u0146"+
		"\u0145\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0149\3\2\2\2\u0148\u014a\5a"+
		"\61\2\u0149\u0148\3\2\2\2\u014a\u014b\3\2\2\2\u014b\u0149\3\2\2\2\u014b"+
		"\u014c\3\2\2\2\u014c\u014e\3\2\2\2\u014d\u0144\3\2\2\2\u014d\u014e\3\2"+
		"\2\2\u014e\u0150\3\2\2\2\u014f\u0126\3\2\2\2\u014f\u013e\3\2\2\2\u0150"+
		"L\3\2\2\2\u0151\u0152\5W,\2\u0152\u0153\5S*\2\u0153\u0154\5\25\13\2\u0154"+
		"\u0155\5U+\2\u0155\u0156\5\25\13\2\u0156\u0157\5U+\2\u0157\u0158\5W,\2"+
		"\u0158N\3\2\2\2\u0159\u015a\5W,\2\u015a\u015b\5U+\2\u015b\u015c\5\7\4"+
		"\2\u015c\u015d\5U+\2\u015d\u015e\5\7\4\2\u015e\u015f\5U+\2\u015f\u0160"+
		"\5W,\2\u0160P\3\2\2\2\u0161\u0162\5W,\2\u0162\u0163\5S*\2\u0163\u0164"+
		"\5\25\13\2\u0164\u0165\5U+\2\u0165\u0166\5\25\13\2\u0166\u0167\5U+\2\u0167"+
		"\u0168\5]/\2\u0168\u0169\5U+\2\u0169\u016a\5\7\4\2\u016a\u016b\5U+\2\u016b"+
		"\u016c\5\7\4\2\u016c\u016d\5U+\2\u016d\u016e\5W,\2\u016eR\3\2\2\2\u016f"+
		"\u0170\5a\61\2\u0170\u0171\5a\61\2\u0171\u0172\5a\61\2\u0172\u0173\5a"+
		"\61\2\u0173T\3\2\2\2\u0174\u0175\5a\61\2\u0175\u0176\5a\61\2\u0176V\3"+
		"\2\2\2\u0177\u0178\t\b\2\2\u0178X\3\2\2\2\u0179\u017d\7A\2\2\u017a\u017c"+
		"\5a\61\2\u017b\u017a\3\2\2\2\u017c\u017f\3\2\2\2\u017d\u017b\3\2\2\2\u017d"+
		"\u017e\3\2\2\2\u017e\u0183\3\2\2\2\u017f\u017d\3\2\2\2\u0180\u0181\t\t"+
		"\2\2\u0181\u0183\5I%\2\u0182\u0179\3\2\2\2\u0182\u0180\3\2\2\2\u0183Z"+
		"\3\2\2\2\u0184\u018a\7)\2\2\u0185\u0189\n\n\2\2\u0186\u0187\7)\2\2\u0187"+
		"\u0189\7)\2\2\u0188\u0185\3\2\2\2\u0188\u0186\3\2\2\2\u0189\u018c\3\2"+
		"\2\2\u018a\u0188\3\2\2\2\u018a\u018b\3\2\2\2\u018b\u018d\3\2\2\2\u018c"+
		"\u018a\3\2\2\2\u018d\u018e\7)\2\2\u018e\\\3\2\2\2\u018f\u0190\t\13\2\2"+
		"\u0190\u0191\3\2\2\2\u0191\u0192\b/\2\2\u0192^\3\2\2\2\u0193\u0194\13"+
		"\2\2\2\u0194`\3\2\2\2\u0195\u0196\t\f\2\2\u0196b\3\2\2\2\u0197\u0198\t"+
		"\r\2\2\u0198d\3\2\2\2\u0199\u019a\t\16\2\2\u019af\3\2\2\2\u019b\u019c"+
		"\t\17\2\2\u019ch\3\2\2\2\u019d\u019e\t\20\2\2\u019ej\3\2\2\2\u019f\u01a0"+
		"\t\21\2\2\u01a0l\3\2\2\2\u01a1\u01a2\t\22\2\2\u01a2n\3\2\2\2\u01a3\u01a4"+
		"\t\23\2\2\u01a4p\3\2\2\2\u01a5\u01a6\t\24\2\2\u01a6r\3\2\2\2\u01a7\u01a8"+
		"\t\25\2\2\u01a8t\3\2\2\2\u01a9\u01aa\t\26\2\2\u01aav\3\2\2\2\u01ab\u01ac"+
		"\t\27\2\2\u01acx\3\2\2\2\u01ad\u01ae\t\30\2\2\u01aez\3\2\2\2\u01af\u01b0"+
		"\t\31\2\2\u01b0|\3\2\2\2\u01b1\u01b2\t\32\2\2\u01b2~\3\2\2\2\u01b3\u01b4"+
		"\t\33\2\2\u01b4\u0080\3\2\2\2\u01b5\u01b6\t\34\2\2\u01b6\u0082\3\2\2\2"+
		"\u01b7\u01b8\t\35\2\2\u01b8\u0084\3\2\2\2\u01b9\u01ba\t\36\2\2\u01ba\u0086"+
		"\3\2\2\2\u01bb\u01bc\t\37\2\2\u01bc\u0088\3\2\2\2\u01bd\u01be\t \2\2\u01be"+
		"\u008a\3\2\2\2\u01bf\u01c0\t!\2\2\u01c0\u008c\3\2\2\2\u01c1\u01c2\t\""+
		"\2\2\u01c2\u008e\3\2\2\2\u01c3\u01c4\t#\2\2\u01c4\u0090\3\2\2\2\u01c5"+
		"\u01c6\t$\2\2\u01c6\u0092\3\2\2\2\u01c7\u01c8\t%\2\2\u01c8\u0094\3\2\2"+
		"\2\u01c9\u01ca\t&\2\2\u01ca\u0096\3\2\2\2\31\2\u0104\u0106\u010e\u0110"+
		"\u0118\u0120\u0123\u0128\u012e\u0131\u0135\u013a\u013c\u0142\u0146\u014b"+
		"\u014d\u014f\u017d\u0182\u0188\u018a\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}