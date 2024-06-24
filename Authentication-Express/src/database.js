const mongoose = require('mongoose');
require('dotenv').config();

const { MI_RUTA_ATLAS, MI_RUTA_MONGODB_HOST, MI_RUTA_MONGODB_PORT, MI_RUTA_MONGODB_DATABASE } = process.env;

//Base de datos local
const MONGODB_URI = `mongodb://${MI_RUTA_MONGODB_HOST}:${MI_RUTA_MONGODB_PORT}/${MI_RUTA_MONGODB_DATABASE}`;

//Cluster mongoatlas
//const MONGODB_URI = MI_RUTA_ATLAS;

mongoose
    .connect(MONGODB_URI, {
        useNewUrlParser: true,
    })
    .then((db) => console.log('Database connected'))
    .catch((err) => console.log(err));
