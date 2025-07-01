package co.gestion.canchas.reservas.autenticacion.utils.constants;

import jakarta.servlet.http.HttpServletResponse;

import java.time.format.DateTimeFormatter;

public class Constantes {

    // Roles
    public static final String ROL_USER = "USER";

    // Constantes de configuración
    public static final String ERROR_USUARIO_NO_ENCONTRADO = "Error al buscar el usuario: ";
    public static final String ORIGEN_DESARROLLO = "http://localhost:4200";

    // Rutas
    public static final String V3_API_DOCS_PATH = "/api-docs/**";
    public static final String SWAGGER_UI_PATH = "/doc/swagger-ui/***";
    public static final String USER_PATH = "/usuario/**";


    // Claims y mensajes relacionados con JWT
    public static final String CLAIM_CODIGO_USER = "codigoUsuario";
    public static final String ERROR_IDENTIFICADOR_NO_VALIDO = "El token no contiene un identificador válido.";
    public static final long EXPIRATION_TIME_IN_MILLIS = 24 * 60 * 60 * 1000; // 24 horas

    // Respuesta HTTP: contenido y estados
    public static final String RESPONSE_CONTENT_TYPE = "application/json";
    public static final int RESPONSE_STATUS_FORBIDDEN = HttpServletResponse.SC_FORBIDDEN;
    public static final String ACCESS_DENIED_MESSAGE = "{\"error\": \"Acceso denegado: No tienes permiso para acceder a este recurso.\"}";

    public static final int RESPONSE_STATUS_UNAUTHORIZED = HttpServletResponse.SC_UNAUTHORIZED;
    public static final String AUTHENTICATION_FAILED_MESSAGE = "Autenticación fallida: ";

    // Mensajes de error en logs
    public static final String ERRORES_VALIDACIONES = "Errores de validación: {}";
    public static final String RECURSO_NO_ENCONTRADO = "Recurso no encontrado: {}";
    public static final String ERROR_INESPERADO = "Error inesperado: {}";

    // Claves y mensajes de respuesta
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String PATH_KEY = "path";
    public static final String STATUS_KEY = "status";
    public static final String MESSAGE_KEY = "message";
    public static final String ERRORS_KEY = "errors";
    public static final String VALIDATION_ERRORS_MESSAGE = "Errores de validación";
    public static final String NOT_FOUND_ERROR = "No encontrado";
    public static final String INTERNAL_SERVER_ERROR = "Error interno del servidor";

    // Prefijo y tamaño de tokens Bearer
    public static final String BEARER_PREFIX = "Bearer ";
    public static final int SIZE_BEARER = 7;

    //formatos de hora y feccha
    public static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");

    //plantilla mensaje correo
    public static final String PLANTILLA_MENSAJE = """
        Estimado/a %s,

        Nos complace informarle que su reserva ha sido procesada correctamente. A continuación, encontrará los detalles de su reserva:

        Número de reserva: #%s
        Fecha de reserva: %s
        Hora de uso: %s
        Cancha: %s

        Si necesita realizar alguna modificación o tiene cualquier consulta adicional, no dude en ponerse en contacto con nosotros respondiendo a este correo %s.

        ¡Esperamos verle pronto!
        """;

}
