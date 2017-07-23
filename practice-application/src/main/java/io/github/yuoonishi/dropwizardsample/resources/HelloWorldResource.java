package io.github.yuoonishi.dropwizardsample.resources;

import static com.google.common.html.HtmlEscapers.htmlEscaper;

import io.github.yuoonishi.dropwizardsample.PracticeConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.annotations.VisibleForTesting;

import java.io.PrintStream;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("/")
public class HelloWorldResource {

    @Inject
    @VisibleForTesting
    PracticeConfiguration configuration;

    /** Prints Hello, World! on HTML. */
    @GET
    @Produces(MediaType.TEXT_HTML + "; charset=UTF-8")
    public Response hello(@Context final ContainerRequestContext crc) {
        return Response.ok((StreamingOutput) ((output) -> {
            try (final PrintStream ps = new PrintStream(output, false, "UTF-8")) {
                ps.println("<h1>Hello Dropwizard!</h1>");

                ps.println("<h2>Configurations</h2>");
                ps.println("<pre>");
                final ObjectMapper jsonMapper = new ObjectMapper();
                jsonMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                ps.println(htmlEscaper().escape(jsonMapper.writeValueAsString(configuration)));
                ps.println("</pre>");

                ps.println("<h2>Request Headers</h2>");
                ps.println("<table border=\"1\">");
                new TreeMap<String, List<String>>(crc.getHeaders()).forEach((key, values) -> {
                    values.forEach(value -> {
                        ps.printf("<tr><th>%s</th><td>%s</td></tr>%n", htmlEscaper().escape(key),
                                htmlEscaper().escape(value));
                    });
                });
                ps.println("</table>");
            }
        })).build();
    }

}
