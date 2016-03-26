To build:

   gradle dist

To start:

   cd build/distributions/main
   ./madlibs start

To stop:

   cd build/distributions/main
   ./madlibs stop

Notes:

   * MongoDB 3.x must be running on localhost.
   * The log file is app.log.
   * The app structure is based on services and server-side generation of HTML.
     Static web pages with JavaScript to control the page flow might have been
     better.