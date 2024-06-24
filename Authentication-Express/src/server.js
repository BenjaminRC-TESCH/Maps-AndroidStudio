//Correr el servidor con npm run dev cuando se este en desarrollo
//Correr el servidor con npm start cuando se vaya a desplegar la aplicacion
const express = require('express');
const path = require('path');
const exphbs = require('express-handlebars');
const morgan = require('morgan');
const methodOverride = require('method-override');
const flash = require('connect-flash');
const session = require('express-session');
const passport = require('passport');

//initializations
const app = express();
require('./config/passport');

//settings
app.set('port', process.env.PORT || 4000);

//middelwards
app.use(morgan('dev'));
app.use(express.json());
app.use(passport.initialize());

app.use(methodOverride('_method'));

app.use(require('./routes/auth.routes'));
app.use(require('./routes/profile.routes'));

module.exports = app;
