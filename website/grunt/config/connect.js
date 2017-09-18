module.exports = function(options) {
  var PORT = 8080;

  return {
    options: {
      port: PORT,
      hostname: '*',
      open: true
    },
    devServer: {
      options: {
        base: options.webApp.source
      }
    },
    browser: {
      options: {
        open: {
          target: 'http://localhost:8080'
        }
      }
    }
  };
};
