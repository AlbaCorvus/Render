package jschimera.loc.render.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import jschimera.loc.asset.domain.ShaderDetails;

public class ShaderReader implements IOReader<ShaderDetails,String>{

	@Override
	public ShaderDetails read(String value) {
		String vertexSource ="";
		String fragmentSource = "";
		try {
			InputStream input = ClassLoader.getSystemResourceAsStream(value);
			byte[] bytes = new byte[input.available()];
			DataInputStream dataInputStream = new DataInputStream(input);
			dataInputStream.readFully(bytes);
            String source = new String(bytes);
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            // Find the first pattern after #type 'pattern'
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();

            // Find the second pattern after #type 'pattern'
            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected token '" + firstPattern + "'");
            }

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected token '" + secondPattern + "'");
            }
        } catch(IOException e) {
            e.printStackTrace();
            assert false : "Error: Could not open file for shader: '" + value + "'";
        }
		return new ShaderDetails(vertexSource,fragmentSource);
	}

}
