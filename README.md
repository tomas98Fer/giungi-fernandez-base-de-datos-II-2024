# giungi-fernandez-base-de-datos-II-2024


Programa que analiza dos bases de datos cuyos datos pueden ser pasados por consola o cargara datos por default desde un archivo de configuracion config.properties y almacena las diferencias de ambas en un archivo "report_diff.txt".

En el menu se puede elegir la forma en la que se obtendran los datos para la conexion.

El programa funciona para los motores de BDD postresql, mysql y oracle.

El reporte que enuncia las diferencias entre ambas db se encuentra en "File/report_diff.txt".

En el reporte se veran las diferencias entre las tablas, a nivel de columnas, claves(primarias,unicas, foraneas e indices) y triggers.
Tambien se veran las diferencias entre los procedimientos de cada BDD.

Los scripts de pruebas se encuentran en la carpeta "Scripts".
