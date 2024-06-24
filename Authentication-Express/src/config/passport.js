const passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;
const User = require('../models/users');

passport.use(
    new LocalStrategy(
        {
            usernameField: 'email',
            passwordField: 'password',
        },
        async (email, password, done) => {
            try {
                // Buscar el usuario por nombre de usuario
                const user = await User.findOne({ email });
                if (!user) {
                    return done(null, false, { message: 'Usuario no encontrado' });
                }

                // Verificar la contraseña
                const isMatch = await user.matchPassword(password);
                if (!isMatch) {
                    return done(null, false, { message: 'Contraseña incorrecta' });
                }

                // Si la autenticación es exitosa, devolver el usuario
                return done(null, user);
            } catch (error) {
                return done(error);
            }
        }
    )
);

passport.serializeUser((user, done) => {
    done(null, user.id);
});

passport.deserializeUser((id, done) => {
    User.findById(id)
        .exec()
        .then((user) => {
            done(null, user); // Manejar errores de findById de forma más robusta si es necesario
        })
        .catch((err) => {
            done(err);
        });
});
