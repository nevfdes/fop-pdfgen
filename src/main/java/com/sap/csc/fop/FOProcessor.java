package com.sap.csc.fop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryConfig;
import org.apache.xmlgraphics.util.MimeConstants;

public class FOProcessor {

	public static void main(String args[]) {
		try {
			ByteArrayOutputStream fopout = new ByteArrayOutputStream();
			File fopConfFile = new File(FOProcessor.class.getResource("/fop.xconf").getFile());
			FopFactory fopFactory = FopFactory.newInstance(fopConfFile);

			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, fopout);
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer(new StreamSource(FOProcessor.class.getResourceAsStream("/template.xsl")));

			transformer.transform(new StreamSource(FOProcessor.class.getResourceAsStream("/data.xml")),
					new SAXResult(fop.getDefaultHandler()));

			File pdffile = new File("/Users/i322080/Documents/Result.pdf");
			OutputStream out = new java.io.FileOutputStream(pdffile);
			out = new java.io.BufferedOutputStream(out);
			FileOutputStream str = new FileOutputStream(pdffile);
			str.write(fopout.toByteArray());
			str.close();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
