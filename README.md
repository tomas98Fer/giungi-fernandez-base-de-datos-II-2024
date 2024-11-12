# giungi-fernandez-base-de-datos-II-2024

Autores: Giungi Agustín, Fernández Tomás
Año: 2024
Materia: Base de Datos II (2024) - Código: 3335
Universidad Nacional de Río Cuarto
Facultad de Ciencias Exactas, Físico-Químicas y Naturales

ComparatorDB:

Esta herramienta genera un reporte en un archivo con las diferencias estructurales entre dos bases de datos del mismo motor. Los datos de conexión de cada base de datos se reciben como parámetros. A partir de esta información, el sistema extrae los metadatos y genera un informe detallado con las diferencias detectadas entre ambas bases de datos.


Si desea cargar valores por defecto, puede hacerlo cargando el archivo config.properties que se encuentra en el directorio resources. En él se incluyen las librerías .jar de los drivers de los respectivos motores de base de datos.

Si prefiere, también puede cargar los datos manualmente. La aplicación dispone de un menú en el que solo debe seleccionar la opción 1.

Para compilar este proyecto, sitúese en la raíz del directorio comparatorDB y use el siguiente comando:


mvn compile


Para ejecutar el proyecto, una vez compilado, utilice:


mvn exec:java


Antes de finalizar, el programa avisará que ha generado el reporte. Este reporte se encuentra en el directorio comparatorDB/files/report_diff.txt.
