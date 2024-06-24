const app = require('./server');
require('./database');
require('dotenv').config;

app.listen(app.get('port'), () => {
    console.log('Server on port ', app.get('port'));
});
