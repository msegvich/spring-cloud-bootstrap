module.exports = function(grunt) {
  'use strict';

  const gruntConfigPath = './grunt/config/';
  const gruntTaskPath = './grunt/tasks/';

  const webAppConfig = require('./grunt/webapp.config.js')();

  const options = {
    webApp: webAppConfig,  
  };

  // Pass grunt to child configurations
  // Mainly for file manipulation in the specific configs
  options.grunt = grunt;

  options.log = function(value) {
    console.log(util.inspect(value, {
      colors: true,
      showHidden: false,
      depth: null
    }));
  };

  function loadConfig(path, options) {
    const glob = require('glob');
    const object = {};
    let key;

    glob.sync('*.js', {
      cwd: path
    }).forEach(function (option) {
      key = option.replace(/\.js$/, '');
      object[key] = require(path + option)(options);
    });

    return object;
  }

  //Load the config and initialize grunt
  const gruntConfig = loadConfig(gruntConfigPath, options);
  grunt.initConfig(gruntConfig);

  // Auto load tasks from dependencies in package.json
  require('load-grunt-tasks')(grunt);

  // Load tasks from separate JS files
  grunt.loadTasks(gruntTaskPath);

  // Custom tasks below
  grunt.registerTask('rebuild', [
      'clean',
      'copy:build',
  ]);

  grunt.registerTask('bootRun', [
      'rebuild',
      'connect:devServer',
      'keepalive'
  ]);
};
