link a mi repositorio: https://github.com/pablonso308/NovelManager.git

Biblioteca Personal de Novelas
Esta aplicación para Android permite a los usuarios gestionar su biblioteca personal de novelas, facilitando el almacenamiento, organización y configuración de sus preferencias. La aplicación ofrece opciones de almacenamiento y configuración avanzada, incluyendo almacenamiento en bases de datos y preferencia de temas. Además, el usuario puede realizar copias de seguridad y restaurar datos.

Características Principales
Almacenamiento de Preferencias del Usuario

Utiliza SharedPreferences para guardar configuraciones personalizadas, tales como el tema de la aplicación (modo claro u oscuro) y preferencias de visualización, asegurando una experiencia personalizada y persistente para cada usuario.
Almacenamiento Interno y Externo

Implementa opciones de almacenamiento en el dispositivo, utilizando almacenamiento interno y almacenamiento externo según el tipo de datos. Esto permite que el usuario pueda elegir entre guardar información localmente o en medios externos, optimizando así la gestión de recursos y accesibilidad.
Base de Datos SQLite

Almacena y gestiona la información de las novelas utilizando una base de datos SQLite integrada. Esto garantiza la persistencia y organización de los datos de las novelas, permitiendo búsquedas y accesos rápidos en una estructura de datos bien definida.
Configuración de la Aplicación

Incluye una pantalla de Ajustes donde el usuario puede configurar opciones de la aplicación, como realizar copias de seguridad y restaurar datos desde el almacenamiento externo. Estas opciones le dan al usuario el control sobre la gestión de su información y le proporcionan una capa adicional de seguridad de sus datos.
Interfaz de Usuario Intuitiva

La aplicación presenta una interfaz clara y fácil de usar, diseñada con vistas y layouts apropiados que optimizan la navegación y presentación de la información. Se ha puesto especial atención en la organización visual para facilitar una experiencia de usuario óptima.
Requisitos Previos
Para compilar y ejecutar esta aplicación, asegúrate de tener instalado:

Android Studio 4.0 o superior
Un dispositivo Android o emulador con Android 5.0 o superior
Instrucciones de Instalación
Clona este repositorio en tu máquina local.
bash
Copiar código
git clone https://github.com/tu-usuario/biblioteca-novelas.git
Abre el proyecto en Android Studio.
Conecta un dispositivo Android o inicia un emulador.
Compila y ejecuta la aplicación presionando Run en Android Studio.
Uso de la Aplicación
Navegación y Añadir Novelas: Desde la pantalla principal, el usuario puede añadir, eliminar o ver información detallada de cada novela.
Configuración de Preferencias: En el menú de ajustes, es posible cambiar el tema (claro/oscuro) y otras opciones de visualización.
Copia de Seguridad y Restauración: Desde la configuración, el usuario puede crear copias de seguridad de sus datos en el almacenamiento externo y restaurarlos en cualquier momento.
Estructura del Proyecto
main/java: Contiene las clases principales de la aplicación.
activities: Actividades de la aplicación.
database: Configuración y gestión de la base de datos SQLite.
utils: Clases de utilidades para el almacenamiento en SharedPreferences y archivos.
res/layout: Layouts XML para las pantallas y elementos de la interfaz de usuario.
res/values: Recursos de valores como cadenas de texto y estilos de temas.
Tecnologías Utilizadas
Java/Kotlin: Lenguaje principal de desarrollo.
SQLite: Base de datos local para almacenamiento de información de las novelas.
SharedPreferences: Para el almacenamiento de configuraciones de usuario.
Almacenamiento Interno y Externo: Para gestionar los datos de manera más flexible y accesible.
Mejoras Futuras
Algunas posibles extensiones para esta aplicación incluyen:

Implementación de sincronización en la nube para respaldar la base de datos.
Funcionalidad de exportación/importación de datos en formatos comunes como CSV.
Integración de un sistema de recomendaciones basado en las novelas agregadas.
Contribuciones
Las contribuciones a este proyecto son bienvenidas. Si tienes ideas o mejoras, abre un issue o envía un pull request.

# Project Optimizations

## Memory Optimization
- **Efficient Data Structures**: Utilized efficient data structures to minimize memory usage.
- **Bitmap Management**: Implemented proper bitmap management to avoid memory leaks.
- **Garbage Collection**: Ensured timely garbage collection by avoiding memory leaks and unnecessary object references.

## Network Optimization
- **Caching**: Implemented caching mechanisms to reduce network calls and improve performance.
- **Compression**: Enabled data compression to reduce the amount of data transmitted over the network.
- **Efficient API Calls**: Optimized API calls to fetch only necessary data and reduce payload size.

## Battery Optimization
- **Background Tasks**: Minimized background tasks and ensured they run only when necessary.
- **JobScheduler**: Used `JobScheduler` for scheduling background tasks efficiently.
- **Battery Saver Mode**: Implemented battery saver mode to reduce power consumption when the device is low on battery.

## Implementation Details

### Memory Optimization
- **Efficient Data Structures**: Replaced `ArrayList` with `SparseArray` where applicable.
- **Bitmap Management**: Used `BitmapFactory.Options` to downsample images and reduce memory usage.
- **Garbage Collection**: Ensured proper use of `WeakReference` to avoid memory leaks.

### Network Optimization
- **Caching**: Implemented HTTP response caching using `OkHttp` library.
- **Compression**: Enabled GZIP compression for network requests.
- **Efficient API Calls**: Used pagination and selective data fetching to reduce network load.

### Battery Optimization
- **Background Tasks**: Reduced the frequency of background tasks and used `WorkManager` for efficient task scheduling.
- **JobScheduler**: Scheduled background tasks using `JobScheduler` to run during optimal conditions.
- **Battery Saver Mode**: Implemented a battery saver mode that reduces the frequency of background updates and disables non-essential features.

## Conclusion
These optimizations help in improving the overall performance of the application by reducing memory usage, minimizing network calls, and conserving battery life. The implementation of these optimizations ensures a smoother and more efficient user experience.