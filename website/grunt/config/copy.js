module.exports = function (options) {
  const environment = options.grunt.option('environment') || 'dev';
  
  const build = {
    files: [
    {
      cwd: options.webApp.source,
      src: [
        '**',
        '!' + options.webApp.build.path + options.webApp.allFiles,
      ],
      dest: options.webApp.build.path,
      expand: true,
      rename: function(dest, src) {
        return dest + '/' + src;
      }
    }
    ]
  };

  const copyConfig = {
    build: build
  }

  return copyConfig;
};
