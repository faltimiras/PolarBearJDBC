// Generated from Sql.g4 by ANTLR 4.7.2
package cat.altimiras.jdbc.polarbear.query.antlr4;

import java.util.List;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SqlParser extends Parser {
	public static final int
		SCOL = 1, DOT = 2, TWO_DOTS = 3, OPEN_PAR = 4, CLOSE_PAR = 5, COMMA = 6, ASSIGN = 7,
		STAR = 8, PLUS = 9, MINUS = 10, TILDE = 11, PIPE2 = 12, DIV = 13, MOD = 14, LT2 = 15,
		GT2 = 16, AMP = 17, PIPE = 18, LT = 19, LT_EQ = 20, GT = 21, GT_EQ = 22, EQ = 23, NOT_EQ1 = 24,
		NOT_EQ2 = 25, K_AND = 26, K_OR = 27, K_NOT = 28, K_FROM = 29, K_SELECT = 30, K_WHERE = 31,
		K_AS = 32, K_IN = 33, K_LIKE = 34, K_REGEXP = 35, IDENTIFIER = 36, NUMERIC_LITERAL = 37,
		DATE = 38, TIME = 39, DATE_TIME = 40, FOUR_DIGITS = 41, TWO_DIGITS = 42, QUOTE = 43,
		BIND_PARAMETER = 44, STRING_LITERAL = 45, SPACES = 46, UNEXPECTED_CHAR = 47, K_ALL = 48;

	public static final int
		RULE_select = 0, RULE_table = 1, RULE_result_column = 2, RULE_expr = 3,
		RULE_comparator = 4, RULE_binary = 5, RULE_literal_value = 6, RULE_column_alias = 7,
		RULE_database_name = 8, RULE_table_name = 9, RULE_table_alias = 10, RULE_column_name = 11,
		RULE_any_name = 12;

	public static final String[] ruleNames = makeRuleNames();

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\62\u0093\4\2\t\2" +
			"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
			"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\5\2\37\n\2\3\2\3\2\3\2\7\2$\n" +
			"\2\f\2\16\2\'\13\2\3\2\3\2\3\2\3\2\7\2-\n\2\f\2\16\2\60\13\2\5\2\62\n" +
			"\2\3\2\3\2\5\2\66\n\2\3\3\3\3\3\3\5\3;\n\3\3\3\3\3\5\3?\n\3\3\3\5\3B\n" +
			"\3\3\4\3\4\3\4\5\4G\n\4\3\4\3\4\3\4\3\4\5\4M\n\4\3\4\3\4\5\4Q\n\4\3\4" +
			"\5\4T\n\4\5\4V\n\4\3\5\3\5\3\5\3\5\3\5\5\5]\n\5\3\5\3\5\3\5\5\5b\n\5\3" +
			"\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5k\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7" +
			"\5u\n\5\f\5\16\5x\13\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3" +
			"\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0091\n\16" +
			"\3\16\2\3\b\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\6\5\2\t\t\25\33#%\3" +
			"\2\34\35\4\2\'*//\4\2&&//\2\u009c\2\34\3\2\2\2\4:\3\2\2\2\6U\3\2\2\2\b" +
			"j\3\2\2\2\ny\3\2\2\2\f{\3\2\2\2\16}\3\2\2\2\20\177\3\2\2\2\22\u0081\3" +
			"\2\2\2\24\u0083\3\2\2\2\26\u0085\3\2\2\2\30\u0087\3\2\2\2\32\u0090\3\2" +
			"\2\2\34\36\7 \2\2\35\37\7\62\2\2\36\35\3\2\2\2\36\37\3\2\2\2\37 \3\2\2" +
			"\2 %\5\6\4\2!\"\7\b\2\2\"$\5\6\4\2#!\3\2\2\2$\'\3\2\2\2%#\3\2\2\2%&\3" +
			"\2\2\2&\61\3\2\2\2\'%\3\2\2\2()\7\37\2\2).\5\4\3\2*+\7\b\2\2+-\5\4\3\2" +
			",*\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\61" +
			"(\3\2\2\2\61\62\3\2\2\2\62\65\3\2\2\2\63\64\7!\2\2\64\66\5\b\5\2\65\63" +
			"\3\2\2\2\65\66\3\2\2\2\66\3\3\2\2\2\678\5\22\n\289\7\4\2\29;\3\2\2\2:" +
			"\67\3\2\2\2:;\3\2\2\2;<\3\2\2\2<A\5\24\13\2=?\7\"\2\2>=\3\2\2\2>?\3\2" +
			"\2\2?@\3\2\2\2@B\5\26\f\2A>\3\2\2\2AB\3\2\2\2B\5\3\2\2\2CD\5\24\13\2D" +
			"E\7\4\2\2EG\3\2\2\2FC\3\2\2\2FG\3\2\2\2GH\3\2\2\2HV\7\n\2\2IJ\5\24\13" +
			"\2JK\7\4\2\2KM\3\2\2\2LI\3\2\2\2LM\3\2\2\2MN\3\2\2\2NS\5\30\r\2OQ\7\"" +
			"\2\2PO\3\2\2\2PQ\3\2\2\2QR\3\2\2\2RT\5\20\t\2SP\3\2\2\2ST\3\2\2\2TV\3" +
			"\2\2\2UF\3\2\2\2UL\3\2\2\2V\7\3\2\2\2WX\b\5\1\2Xk\5\16\b\2YZ\5\22\n\2" +
			"Z[\7\4\2\2[]\3\2\2\2\\Y\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^_\5\24\13\2_`\7\4" +
			"\2\2`b\3\2\2\2a\\\3\2\2\2ab\3\2\2\2bc\3\2\2\2ck\5\30\r\2de\7\36\2\2ek" +
			"\5\b\5\6fg\7\6\2\2gh\5\b\5\2hi\7\7\2\2ik\3\2\2\2jW\3\2\2\2ja\3\2\2\2j" +
			"d\3\2\2\2jf\3\2\2\2kv\3\2\2\2lm\f\5\2\2mn\5\n\6\2no\5\b\5\6ou\3\2\2\2" +
			"pq\f\3\2\2qr\5\f\7\2rs\5\b\5\4su\3\2\2\2tl\3\2\2\2tp\3\2\2\2ux\3\2\2\2" +
			"vt\3\2\2\2vw\3\2\2\2w\t\3\2\2\2xv\3\2\2\2yz\t\2\2\2z\13\3\2\2\2{|\t\3" +
			"\2\2|\r\3\2\2\2}~\t\4\2\2~\17\3\2\2\2\177\u0080\t\5\2\2\u0080\21\3\2\2" +
			"\2\u0081\u0082\5\32\16\2\u0082\23\3\2\2\2\u0083\u0084\5\32\16\2\u0084" +
			"\25\3\2\2\2\u0085\u0086\5\32\16\2\u0086\27\3\2\2\2\u0087\u0088\5\32\16" +
			"\2\u0088\31\3\2\2\2\u0089\u0091\7&\2\2\u008a\u0091\7\"\2\2\u008b\u0091" +
			"\7/\2\2\u008c\u008d\7\6\2\2\u008d\u008e\5\32\16\2\u008e\u008f\7\7\2\2" +
			"\u008f\u0091\3\2\2\2\u0090\u0089\3\2\2\2\u0090\u008a\3\2\2\2\u0090\u008b" +
			"\3\2\2\2\u0090\u008c\3\2\2\2\u0091\33\3\2\2\2\25\36%.\61\65:>AFLPSU\\" +
			"ajtv\u0090";

	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());

	protected static final DFA[] _decisionToDFA;

	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();

	private static final String[] _LITERAL_NAMES = makeLiteralNames();

	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();

	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	static {
		RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
	}

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

	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}

	public SqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	private static String[] makeRuleNames() {
		return new String[]{
			"select", "table", "result_column", "expr", "comparator", "binary", "literal_value",
			"column_alias", "database_name", "table_name", "table_alias", "column_name",
			"any_name"
		};
	}

	private static String[] makeLiteralNames() {
		return new String[]{
			null, "';'", "'.'", "':'", "'('", "')'", "','", "'='", "'*'", "'+'",
			"'-'", "'~'", "'||'", "'/'", "'%'", "'<<'", "'>>'", "'&'", "'|'", "'<'",
			"'<='", "'>'", "'>='", "'=='", "'!='", "'<>'"
		};
	}

	private static String[] makeSymbolicNames() {
		return new String[]{
			null, "SCOL", "DOT", "TWO_DOTS", "OPEN_PAR", "CLOSE_PAR", "COMMA", "ASSIGN",
			"STAR", "PLUS", "MINUS", "TILDE", "PIPE2", "DIV", "MOD", "LT2", "GT2",
			"AMP", "PIPE", "LT", "LT_EQ", "GT", "GT_EQ", "EQ", "NOT_EQ1", "NOT_EQ2",
			"K_AND", "K_OR", "K_NOT", "K_FROM", "K_SELECT", "K_WHERE", "K_AS", "K_IN",
			"K_LIKE", "K_REGEXP", "IDENTIFIER", "NUMERIC_LITERAL", "DATE", "TIME",
			"DATE_TIME", "FOUR_DIGITS", "TWO_DIGITS", "QUOTE", "BIND_PARAMETER",
			"STRING_LITERAL", "SPACES", "UNEXPECTED_CHAR", "K_ALL"
		};
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

	@Override
	public String getGrammarFileName() {
		return "Sql.g4";
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public final SelectContext select() throws RecognitionException {
		SelectContext _localctx = new SelectContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_select);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(26);
				match(K_SELECT);
				setState(28);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == K_ALL) {
					{
						setState(27);
						match(K_ALL);
					}
				}

				setState(30);
				result_column();
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == COMMA) {
					{
						{
							setState(31);
							match(COMMA);
							setState(32);
							result_column();
						}
					}
					setState(37);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == K_FROM) {
					{
						setState(38);
						match(K_FROM);
						setState(39);
						table();
						setState(44);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la == COMMA) {
							{
								{
									setState(40);
									match(COMMA);
									setState(41);
									table();
								}
							}
							setState(46);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
					}
				}

				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == K_WHERE) {
					{
						setState(49);
						match(K_WHERE);
						setState(50);
						expr(0);
					}
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final TableContext table() throws RecognitionException {
		TableContext _localctx = new TableContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_table);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(56);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
					case 1: {
						setState(53);
						database_name();
						setState(54);
						match(DOT);
					}
					break;
				}
				setState(58);
				table_name();
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0
					&& ((1L << _la) & ((1L << OPEN_PAR) | (1L << K_AS) | (1L << IDENTIFIER) | (1L << STRING_LITERAL)))
					!= 0)) {
					{
						setState(60);
						_errHandler.sync(this);
						switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
							case 1: {
								setState(59);
								match(K_AS);
							}
							break;
						}
						setState(62);
						table_alias();
					}
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final Result_columnContext result_column() throws RecognitionException {
		Result_columnContext _localctx = new Result_columnContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_result_column);
		int _la;
		try {
			setState(83);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(68);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 &&
						((1L << _la) & ((1L << OPEN_PAR) | (1L << K_AS) | (1L << IDENTIFIER) | (1L << STRING_LITERAL)))
							!= 0)) {
						{
							setState(65);
							table_name();
							setState(66);
							match(DOT);
						}
					}

					setState(70);
					match(STAR);
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(74);
					_errHandler.sync(this);
					switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
						case 1: {
							setState(71);
							table_name();
							setState(72);
							match(DOT);
						}
						break;
					}
					setState(76);
					column_name();
					setState(81);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0
						&& ((1L << _la) & ((1L << K_AS) | (1L << IDENTIFIER) | (1L << STRING_LITERAL))) != 0)) {
						{
							setState(78);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if (_la == K_AS) {
								{
									setState(77);
									match(K_AS);
								}
							}

							setState(80);
							column_alias();
						}
					}
				}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(104);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
					case 1: {
						_localctx = new Expr_literalContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;

						setState(86);
						literal_value();
					}
					break;
					case 2: {
						_localctx = new Expr_columnContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(95);
						_errHandler.sync(this);
						switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
							case 1: {
								setState(90);
								_errHandler.sync(this);
								switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
									case 1: {
										setState(87);
										database_name();
										setState(88);
										match(DOT);
									}
									break;
								}
								setState(92);
								table_name();
								setState(93);
								match(DOT);
							}
							break;
						}
						setState(97);
						column_name();
					}
					break;
					case 3: {
						_localctx = new Expr_notContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(98);
						match(K_NOT);
						setState(99);
						expr(4);
					}
					break;
					case 4: {
						_localctx = new Expr_parenthesisContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(100);
						match(OPEN_PAR);
						setState(101);
						expr(0);
						setState(102);
						match(CLOSE_PAR);
					}
					break;
				}
				_ctx.stop = _input.LT(-1);
				setState(116);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null) {
							triggerExitRuleEvent();
						}
						_prevctx = _localctx;
						{
							setState(114);
							_errHandler.sync(this);
							switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
								case 1: {
									_localctx = new Expr_filterContext(new ExprContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(106);
									if (!(precpred(_ctx, 3))) {
										throw new FailedPredicateException(this, "precpred(_ctx, 3)");
									}
									setState(107);
									comparator();
									setState(108);
									expr(4);
								}
								break;
								case 2: {
									_localctx = new Expr_binaryContext(new ExprContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expr);
									setState(110);
									if (!(precpred(_ctx, 1))) {
										throw new FailedPredicateException(this, "precpred(_ctx, 1)");
									}
									setState(111);
									binary();
									setState(112);
									expr(2);
								}
								break;
							}
						}
					}
					setState(118);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public final ComparatorContext comparator() throws RecognitionException {
		ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_comparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(119);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 &&
					((1L << _la) & ((1L << ASSIGN) | (1L << LT) | (1L << LT_EQ) | (1L << GT) | (1L << GT_EQ) | (1L
						<< EQ) | (1L << NOT_EQ1) | (1L << NOT_EQ2) | (1L << K_IN) | (1L << K_LIKE) | (1L << K_REGEXP)))
						!= 0))) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF) {
						matchedEOF = true;
					}
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final BinaryContext binary() throws RecognitionException {
		BinaryContext _localctx = new BinaryContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_binary);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(121);
				_la = _input.LA(1);
				if (!(_la == K_AND || _la == K_OR)) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF) {
						matchedEOF = true;
					}
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final Literal_valueContext literal_value() throws RecognitionException {
		Literal_valueContext _localctx = new Literal_valueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_literal_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(123);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 &&
					((1L << _la) & ((1L << NUMERIC_LITERAL) | (1L << DATE) | (1L << TIME) | (1L << DATE_TIME) | (1L
						<< STRING_LITERAL))) != 0))) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF) {
						matchedEOF = true;
					}
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final Column_aliasContext column_alias() throws RecognitionException {
		Column_aliasContext _localctx = new Column_aliasContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_column_alias);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(125);
				_la = _input.LA(1);
				if (!(_la == IDENTIFIER || _la == STRING_LITERAL)) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF) {
						matchedEOF = true;
					}
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final Database_nameContext database_name() throws RecognitionException {
		Database_nameContext _localctx = new Database_nameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_database_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(127);
				any_name();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final Table_nameContext table_name() throws RecognitionException {
		Table_nameContext _localctx = new Table_nameContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_table_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(129);
				any_name();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final Table_aliasContext table_alias() throws RecognitionException {
		Table_aliasContext _localctx = new Table_aliasContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_table_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(131);
				any_name();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final Column_nameContext column_name() throws RecognitionException {
		Column_nameContext _localctx = new Column_nameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_column_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(133);
				any_name();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public final Any_nameContext any_name() throws RecognitionException {
		Any_nameContext _localctx = new Any_nameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_any_name);
		try {
			setState(142);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case IDENTIFIER:
					enterOuterAlt(_localctx, 1);
				{
					setState(135);
					match(IDENTIFIER);
				}
				break;
				case K_AS:
					enterOuterAlt(_localctx, 2);
				{
					setState(136);
					match(K_AS);
				}
				break;
				case STRING_LITERAL:
					enterOuterAlt(_localctx, 3);
				{
					setState(137);
					match(STRING_LITERAL);
				}
				break;
				case OPEN_PAR:
					enterOuterAlt(_localctx, 4);
				{
					setState(138);
					match(OPEN_PAR);
					setState(139);
					any_name();
					setState(140);
					match(CLOSE_PAR);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
			case 3:
				return expr_sempred((ExprContext) _localctx, predIndex);
		}
		return true;
	}

	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
			case 0:
				return precpred(_ctx, 3);
			case 1:
				return precpred(_ctx, 1);
		}
		return true;
	}

	public static class SelectContext extends ParserRuleContext {
		public SelectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode K_SELECT() {
			return getToken(SqlParser.K_SELECT, 0);
		}

		public List<Result_columnContext> result_column() {
			return getRuleContexts(Result_columnContext.class);
		}

		public Result_columnContext result_column(int i) {
			return getRuleContext(Result_columnContext.class, i);
		}

		public TerminalNode K_ALL() {
			return getToken(SqlParser.K_ALL, 0);
		}

		public List<TerminalNode> COMMA() {
			return getTokens(SqlParser.COMMA);
		}

		public TerminalNode COMMA(int i) {
			return getToken(SqlParser.COMMA, i);
		}

		public TerminalNode K_FROM() {
			return getToken(SqlParser.K_FROM, 0);
		}

		public List<TableContext> table() {
			return getRuleContexts(TableContext.class);
		}

		public TableContext table(int i) {
			return getRuleContext(TableContext.class, i);
		}

		public TerminalNode K_WHERE() {
			return getToken(SqlParser.K_WHERE, 0);
		}

		public ExprContext expr() {
			return getRuleContext(ExprContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_select;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterSelect(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitSelect(this);
			}
		}
	}

	public static class TableContext extends ParserRuleContext {
		public TableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class, 0);
		}

		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class, 0);
		}

		public TerminalNode DOT() {
			return getToken(SqlParser.DOT, 0);
		}

		public Table_aliasContext table_alias() {
			return getRuleContext(Table_aliasContext.class, 0);
		}

		public TerminalNode K_AS() {
			return getToken(SqlParser.K_AS, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_table;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterTable(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitTable(this);
			}
		}
	}

	public static class Result_columnContext extends ParserRuleContext {
		public Result_columnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode STAR() {
			return getToken(SqlParser.STAR, 0);
		}

		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class, 0);
		}

		public TerminalNode DOT() {
			return getToken(SqlParser.DOT, 0);
		}

		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class, 0);
		}

		public Column_aliasContext column_alias() {
			return getRuleContext(Column_aliasContext.class, 0);
		}

		public TerminalNode K_AS() {
			return getToken(SqlParser.K_AS, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_result_column;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterResult_column(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitResult_column(this);
			}
		}
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public ExprContext() {
		}

		@Override
		public int getRuleIndex() {
			return RULE_expr;
		}

		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class Expr_literalContext extends ExprContext {
		public Expr_literalContext(ExprContext ctx) {
			copyFrom(ctx);
		}

		public Literal_valueContext literal_value() {
			return getRuleContext(Literal_valueContext.class, 0);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterExpr_literal(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitExpr_literal(this);
			}
		}
	}

	public static class Expr_parenthesisContext extends ExprContext {
		public Expr_parenthesisContext(ExprContext ctx) {
			copyFrom(ctx);
		}

		public TerminalNode OPEN_PAR() {
			return getToken(SqlParser.OPEN_PAR, 0);
		}

		public ExprContext expr() {
			return getRuleContext(ExprContext.class, 0);
		}

		public TerminalNode CLOSE_PAR() {
			return getToken(SqlParser.CLOSE_PAR, 0);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterExpr_parenthesis(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitExpr_parenthesis(this);
			}
		}
	}

	public static class Expr_filterContext extends ExprContext {
		public Expr_filterContext(ExprContext ctx) {
			copyFrom(ctx);
		}

		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class, i);
		}

		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class, 0);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterExpr_filter(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitExpr_filter(this);
			}
		}
	}

	public static class Expr_columnContext extends ExprContext {
		public Expr_columnContext(ExprContext ctx) {
			copyFrom(ctx);
		}

		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class, 0);
		}

		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class, 0);
		}

		public List<TerminalNode> DOT() {
			return getTokens(SqlParser.DOT);
		}

		public TerminalNode DOT(int i) {
			return getToken(SqlParser.DOT, i);
		}

		public Database_nameContext database_name() {
			return getRuleContext(Database_nameContext.class, 0);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterExpr_column(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitExpr_column(this);
			}
		}
	}

	public static class Expr_notContext extends ExprContext {
		public Expr_notContext(ExprContext ctx) {
			copyFrom(ctx);
		}

		public TerminalNode K_NOT() {
			return getToken(SqlParser.K_NOT, 0);
		}

		public ExprContext expr() {
			return getRuleContext(ExprContext.class, 0);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterExpr_not(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitExpr_not(this);
			}
		}
	}

	public static class Expr_binaryContext extends ExprContext {
		public Expr_binaryContext(ExprContext ctx) {
			copyFrom(ctx);
		}

		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}

		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class, i);
		}

		public BinaryContext binary() {
			return getRuleContext(BinaryContext.class, 0);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterExpr_binary(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitExpr_binary(this);
			}
		}
	}

	public static class ComparatorContext extends ParserRuleContext {
		public ComparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode LT() {
			return getToken(SqlParser.LT, 0);
		}

		public TerminalNode LT_EQ() {
			return getToken(SqlParser.LT_EQ, 0);
		}

		public TerminalNode GT() {
			return getToken(SqlParser.GT, 0);
		}

		public TerminalNode GT_EQ() {
			return getToken(SqlParser.GT_EQ, 0);
		}

		public TerminalNode EQ() {
			return getToken(SqlParser.EQ, 0);
		}

		public TerminalNode ASSIGN() {
			return getToken(SqlParser.ASSIGN, 0);
		}

		public TerminalNode NOT_EQ1() {
			return getToken(SqlParser.NOT_EQ1, 0);
		}

		public TerminalNode NOT_EQ2() {
			return getToken(SqlParser.NOT_EQ2, 0);
		}

		public TerminalNode K_IN() {
			return getToken(SqlParser.K_IN, 0);
		}

		public TerminalNode K_LIKE() {
			return getToken(SqlParser.K_LIKE, 0);
		}

		public TerminalNode K_REGEXP() {
			return getToken(SqlParser.K_REGEXP, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_comparator;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterComparator(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitComparator(this);
			}
		}
	}

	public static class BinaryContext extends ParserRuleContext {
		public BinaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode K_AND() {
			return getToken(SqlParser.K_AND, 0);
		}

		public TerminalNode K_OR() {
			return getToken(SqlParser.K_OR, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_binary;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterBinary(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitBinary(this);
			}
		}
	}

	public static class Literal_valueContext extends ParserRuleContext {
		public Literal_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode NUMERIC_LITERAL() {
			return getToken(SqlParser.NUMERIC_LITERAL, 0);
		}

		public TerminalNode DATE_TIME() {
			return getToken(SqlParser.DATE_TIME, 0);
		}

		public TerminalNode DATE() {
			return getToken(SqlParser.DATE, 0);
		}

		public TerminalNode TIME() {
			return getToken(SqlParser.TIME, 0);
		}

		public TerminalNode STRING_LITERAL() {
			return getToken(SqlParser.STRING_LITERAL, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_literal_value;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterLiteral_value(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitLiteral_value(this);
			}
		}
	}

	public static class Column_aliasContext extends ParserRuleContext {
		public Column_aliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode IDENTIFIER() {
			return getToken(SqlParser.IDENTIFIER, 0);
		}

		public TerminalNode STRING_LITERAL() {
			return getToken(SqlParser.STRING_LITERAL, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_column_alias;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterColumn_alias(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitColumn_alias(this);
			}
		}
	}

	public static class Database_nameContext extends ParserRuleContext {
		public Database_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_database_name;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterDatabase_name(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitDatabase_name(this);
			}
		}
	}

	public static class Table_nameContext extends ParserRuleContext {
		public Table_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_table_name;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterTable_name(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitTable_name(this);
			}
		}
	}

	public static class Table_aliasContext extends ParserRuleContext {
		public Table_aliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_table_alias;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterTable_alias(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitTable_alias(this);
			}
		}
	}

	public static class Column_nameContext extends ParserRuleContext {
		public Column_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_column_name;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterColumn_name(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitColumn_name(this);
			}
		}
	}

	public static class Any_nameContext extends ParserRuleContext {
		public Any_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode IDENTIFIER() {
			return getToken(SqlParser.IDENTIFIER, 0);
		}

		public TerminalNode K_AS() {
			return getToken(SqlParser.K_AS, 0);
		}

		public TerminalNode STRING_LITERAL() {
			return getToken(SqlParser.STRING_LITERAL, 0);
		}

		public TerminalNode OPEN_PAR() {
			return getToken(SqlParser.OPEN_PAR, 0);
		}

		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class, 0);
		}

		public TerminalNode CLOSE_PAR() {
			return getToken(SqlParser.CLOSE_PAR, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_any_name;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).enterAny_name(this);
			}
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SqlListener) {
				((SqlListener) listener).exitAny_name(this);
			}
		}
	}
}