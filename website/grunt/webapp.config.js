module.exports = function() {
  'use strict';

  const source = {
    src: 'src',
    rootPath: 'src',
    source: 'src'
  };

  const build = {
    path: 'build/'
  };

  const webAppConfig = source;
  webAppConfig.build = build;
  webAppConfig.allFiles = '**';

  return webAppConfig;
};
