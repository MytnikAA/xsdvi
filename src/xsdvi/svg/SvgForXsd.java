package xsdvi.svg;

import xsdvi.utils.Resource;
import xsdvi.utils.TreeElement;
import xsdvi.utils.WriterHelper;

/**
 * @author Václav Slavìtínský
 *
 */
public class SvgForXsd {
	protected WriterHelper writer;
	private String styleUri = null;
	private boolean embodyStyle = true;

	/**
	 * 
	 */
	protected static final String XML_DECLARATION =
		"<?xml version='1.0' encoding='UTF-8'?>";
	
	/**
	 * 
	 */
	protected static final String SVG_DOCTYPE =
		"<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'>";
	
	/**
	 * 
	 */
	protected static final String SVG_START =
		"<svg id='svg' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' onload='loadSVG();'>";
	
	/**
	 * 
	 */
	protected static final String TITLE =
		"<title>XsdVi</title>\n";
	
	/**
	 * 
	 */
	protected static final String SCRIPT = Resource.readAsString("svg/logic.js");

	/**
	 * 
	 */
	protected static final String STYLE =
		"svg {pointer-events: none;}\n"+
		"text {font-family: arial; font-size: 11px;}\n"+
		"line, polyline, polygon {fill: none; stroke: black;}\n"+
		"\n"+
		".strong {font-size: 12px; font-weight: bold;}\n"+
		".small {font-size: 10px;}\n"+
		".big {font-size: 15px; fill: #882222;}\n"+
		"\n"+
		".button {fill: white; stroke: black; pointer-events: all;}\n"+
		".shadow {fill: #ccccd8; stroke: none;}\n"+
		".connection {fill: none; stroke: #666666;}\n"+
		".empty {fill: none; stroke: black;}\n"+
		".filled {fill: black; stroke: none;}\n"+
		"\n"+
		".boxelement, .boxany, .boxattribute1, .boxanyattribute {fill: #FFFFBB; stroke: #776633; pointer-events: all;}\n"+
		".boxattribute2 {fill: #FFFFBB; stroke: #776633; pointer-events: all; stroke-dasharray: 2;}\n"+
		".boxschema, .boxloop, .boxcompositor {fill: #E7EBF3; stroke: #666677;}\n"+
		".boxselector, .boxfield, .boxidc {fill: #E0F7B7; stroke: #667733;}\n"+
		"\n"+
		".lax {fill: white; stroke: black;}\n"+
		".skip {fill: #cc6666; stroke: black;}\n"+
		".strict {fill: black; stroke: none;}\n"+
		"\n"+
		".border {fill: #f9f9f9; stroke: #dddddd;}";

	/**
	 * 
	 */
	protected static final String DEFINED_SYMBOLS =
		"  <symbol class='button' id='plus'>\n"+
		"    <rect x='1' y='1' width='10' height='10'/>\n"+
		"    <line x1='3' y1='6' x2='9' y2='6'/>\n"+
		"    <line x1='6' y1='3' x2='6' y2='9'/>\n"+
		"  </symbol>\n"+
		"  <symbol class='button' id='minus'>\n"+
		"    <rect x='1' y='1' width='10' height='10'/>\n"+
		"    <line x1='3' y1='6' x2='9' y2='6'/>\n"+
		"  </symbol>\n";
	
	/**
	 * 
	 */
	protected static final String MENU_BUTTONS =
		"<rect class='button' x='300' y='10' width='20' height='20' onclick='collapseAll()'/>\n"+
		"<line x1='303' y1='20' x2='317' y2='20'/>\n"+
		"<text x='330' y='20'>collapse all</text>\n"+
		"<rect class='button' x='400' y='10' width='20' height='20' onclick='expandAll()'/>\n"+
		"<line x1='403' y1='20' x2='417' y2='20'/>\n"+
		"<line x1='410' y1='13' x2='410' y2='27'/>\n"+
		"<text x='430' y='20'>expand all</text>\n";
	
	/**
	 * 
	 */
	protected static final String SVG_END =
		"</svg>";
	
	/**
	 * @param w
	 */
	public SvgForXsd(WriterHelper w) {
		this.writer = w;
	}
	
	/**
	 * 
	 */
	protected void printStyleRef() {
		print("<?xml-stylesheet href='"+styleUri+"' type='text/css'?>");
	}

	/**
	 * 
	 */
	protected void printEmbodiedStyle() {
		print("<style type='text/css'><![CDATA[");
		print(STYLE);
		print("]]></style>");
	}
	
	/**
	 * @param style
	 * @param symbols
	 */
	protected void printDefs(boolean style, boolean symbols) {
		print("<defs>");
		if (style) {
			printEmbodiedStyle();
		}
		if (symbols) {
			print(DEFINED_SYMBOLS);
		}
        print("</defs>");
	}
	
	/**
	 * 
	 */
	public void printExternStyle() {
		writer.newWriter(styleUri);
		print(STYLE);
		writer.close();
	}
	
	/**
	 * 
	 */
	public void begin() {
		print(XML_DECLARATION);
		if (!embodyStyle) {
			printStyleRef();
		}
		print(SVG_DOCTYPE);
		print(SVG_START);
		print(TITLE);
		
        print(SCRIPT);
        
        printDefs(embodyStyle, true);

    	print(MENU_BUTTONS);
	}

	/**
	 * 
	 */
	public void end() {
		print(SVG_END);
	    writer.close();
	}

	/**
	 * @param string
	 */
	protected void print(String string) {
		writer.append(string+"\n");
	}

	/**
	 * @return
	 */
	public WriterHelper getWriter() {
		return writer;
	}

	/**
	 * @param w
	 */
	public void setWriter(WriterHelper w) {
		this.writer = w;
	}
	
	/**
	 * @param embody
	 */
	public void setEmbodyStyle(boolean embody) {
		embodyStyle = embody;
	}

	/**
	 * @param styleUri
	 */
	public void setStyleUri(String styleUri) {
		this.styleUri = styleUri;
	}

	/**
	 * @return
	 */
	public String getStyleUri() {
		return styleUri;
	}

	/**
	 * @return
	 */
	public boolean embodyStyle() {
		return embodyStyle;
	}

	/**
	 * @param rootSymbol
	 */
	public void draw(AbstractSymbol rootSymbol) {
		begin();
		drawSymbol(rootSymbol);
		end();
	}

	/**
	 * @param symbol
	 */
	private void drawSymbol(AbstractSymbol symbol) {
		symbol.setSvg(this);
		symbol.prepareBox();
		symbol.draw();
		for (TreeElement s : symbol.getChildren()) {
			drawSymbol((AbstractSymbol) s);
		}
	}
}