import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import com.fasterxml.jackson.core.*;

public final class RestServlet extends DatabaseServlet {

	private static final String JSON_MEDIA_TYPE = "application/json";

	private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

	private static final String ALL_MEDIA_TYPE = "*/*";
	
	protected final void service(final HttpServletRequest req, final HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType(JSON_UTF_8_MEDIA_TYPE);
		final OutputStream out = res.getOutputStream();

		try {
			// if the request method and/or the MIME media type are not allowed, return.
			// Appropriate error message sent by {@code checkMethodMediaType}
			if (!checkMethodMediaType(req, res)) {
				return;
			}

			// if the requested resource was an Person, delegate its processing and return
			if (processPerson(req, res)) {
				return;
			}

			// if none of the above process methods succeeds, it means an unknow resource has been requested
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			JSONMessage(out, "Unknown resource requested.", "E4A6",
										  String.format("Requested resource is %s.", req.getRequestURI()));
		} finally {
			// ensure to always flush and close the output stream
			out.flush();
			out.close();
		}
	}

	/**
	 * Checks that the request method and MIME media type are allowed.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @return {@code true} if the request method and the MIME type are allowed; {@code false} otherwise.
	 *
	 * @throws IOException if any error occurs in the client/server communication.
	 */
	private boolean checkMethodMediaType(final HttpServletRequest req, final HttpServletResponse res)
			throws IOException {

		final String method = req.getMethod();
		final String contentType = req.getHeader("Content-Type");
		final String accept = req.getHeader("Accept");
		final OutputStream out = res.getOutputStream();

		if(accept == null) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			JSONMessage(out, "Output media type not specified.", "E4A1", "Accept request header missing.");
			return false;
		}

		if(!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
			res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			JSONMessage(out, "Unsupported output media type. Resources are represented only in application/json.",
							"E4A2", String.format("Requested representation is %s.", accept));
			return false;
		}

		switch(method) {
			case "GET":
			case "DELETE":
				// nothing to do
				break;

			case "POST":
			case "PUT":
				if(contentType == null) {
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					JSONMessage(out, "Input media type not specified.", "E4A3", "Content-Type request header missing.");
					return false;
				}

				if(!contentType.contains(JSON_MEDIA_TYPE)) {
					res.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
					JSONMessage(out, "Unsupported input media type. Resources are represented only in application/json.",
									"E4A4", String.format("Submitted representation is %s.", contentType));
					return false;
				}

				break;
			default:
				res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				JSONMessage(out, "Unsupported operation.",
								"E4A5", String.format("Requested operation %s.", method));
				return false;
		}

		return true;
	}


	/**
	 * Checks whether the request if for an {@link Person} resource and, in case, processes it.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @return {@code true} if the request was for an {@code Person}; {@code false} otherwise.
	 *
	 * @throws IOException if any error occurs in the client/server communication.
	 */
	private boolean processPerson(HttpServletRequest req, HttpServletResponse res) throws IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();

		String path = req.getRequestURI();

		// the requested resource was not an person
		if(path.lastIndexOf("person") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /person
			path = path.substring(path.lastIndexOf("person") + 6);

			// the request URI is: /person
			// if method GET, list person
			// if method POST, create person
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						//new PersonRestResource(req, res, getConnection()).listPerson();
						break;
					case "POST":
						//new PersonRestResource(req, res, getConnection()).createPerson();
						break;
					default:
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						JSONMessage(res.getOutputStream(), "Unsupported operation for URI /person.",
										"E4A5", String.format("Requested operation %s.", method));
						break;
				}
			} else {
					// the request URI is: /person/{idUser}
					try {
						// check that the parameter is actually an integer
						Integer.parseInt(path.substring(1));

						switch (method) {
							case "GET":
								new PersonRestResource(req, res, getConnection()).readPerson();
								break;
							case "PUT":
								//new PersonRestResource(req, res, getConnection()).updatePerson();
								break;
							case "DELETE":
								//new PersonRestResource(req, res, getConnection()).deletePerson();
								break;
							default:
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								JSONMessage(res.getOutputStream(), "Unsupported operation for URI /person/{idUser}.",
												"E4A5", String.format("Requested operation %s.", method));
						}
					} catch (NumberFormatException e) {
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						JSONMessage(res.getOutputStream(), "Wrong format for URI /person/{idUser}: {idUser} is not an integer.",
										"E4A7", e.getMessage());
					}
			}
				
		} catch(Throwable t) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			JSONMessage(res.getOutputStream(), "Unexpected error.", "E5A1", t.getMessage());
		}

		return true;
	}
	
	public static void JSONMessage(final OutputStream out, String message, String errorCode, String errorDetails) throws IOException {
		
		final JsonFactory JSON_FACTORY = new JsonFactory();
		JSON_FACTORY.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
		JSON_FACTORY.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);	
		jg.writeStartObject();
		
		jg.writeFieldName("message");
		
		jg.writeStartObject();
		
		jg.writeStringField("message", message);
		jg.writeStringField("error-code", errorCode);
		jg.writeStringField("error-details", errorDetails);
		
		jg.writeEndObject();
		jg.writeEndObject();
		
		jg.flush();
	}

}
