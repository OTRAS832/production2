// package com.OTRAS.DemoProject.Filter;

// import jakarta.servlet.*;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.stereotype.Component;
// import java.io.IOException;

// @Component
// public class SimpleCORSFilter implements Filter {

//     @Override
//     public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//             throws IOException, ServletException {

//         HttpServletRequest request = (HttpServletRequest) req;
//         HttpServletResponse response = (HttpServletResponse) res;

//         String origin = request.getHeader("Origin");

//         // ✅ Add all allowed origins here
//         if (origin != null && (
//                 origin.equals("https://otrasuser.vercel.app") ||
//                 origin.equals("https://otras-admin-h5q6.vercel.app") ||
//                 origin.equals("https://otras-exam.vercel.app") ||
//                 origin.startsWith("http://localhost:")
//         )) {
//             response.setHeader("Access-Control-Allow-Origin", origin);
//         }

//         response.setHeader("Vary", "Origin");
//         response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//         response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
//         response.setHeader("Access-Control-Allow-Credentials", "true");

//         // ✅ Respond immediately to CORS preflight requests
//         if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//             response.setStatus(HttpServletResponse.SC_OK);
//             return;
//         }

//         chain.doFilter(req, res);
//     }
// }
package com.OTRAS.DemoProject.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");

        // Allowed frontend domains
        String[] allowedOrigins = {
            "https://otras-exam.vercel.app",
            "https://otrasuser.vercel.app",
            "https://otras-admin-h5q6.vercel.app",
            "http://localhost:3000",
            "http://localhost:5173"
        };

        // Dynamic allow-origin
        if (origin != null) {
            for (String allowed : allowedOrigins) {
                if (origin.equals(allowed)) {
                    response.setHeader("Access-Control-Allow-Origin", origin);
                    break;
                }
            }
        }

        response.setHeader("Vary", "Origin");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization, X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");

        // Preflight requests should return 200 directly
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }
}
