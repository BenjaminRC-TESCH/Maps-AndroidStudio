const usersCtrl = {};
const User = require('../models/users');
const passport = require('passport');
require('dotenv').config();

usersCtrl.signup = async (req, res) => {
    const { name, email, password } = req.body;
    const direction = ' ';
    const phone = '';

    if (!name || !email || !password) {
        return res.status(400).json({ message: 'Por favor completa todos los campos.' });
    }

    const nameRegex = /^[A-Za-z]+$/;
    if (!nameRegex.test(name)) {
        return res.status(400).json({ message: 'Por favor ingresa un nombre válido.' });
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        return res.status(400).json({ message: 'Por favor ingresa un correo electrónico válido.' });
    }

    try {
        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(400).json({ message: 'El nombre de usuario ya está en uso.' });
        }

        const newUser = new User({ name, email, password, direction, phone });
        newUser.password = await newUser.encryptPassword(password);
        await newUser.save();

        res.status(200).json({ message: 'Usuario registrado exitosamente.' });
    } catch (error) {
        console.error('Error al registrar usuario:', error);
        res.status(500).json({ message: 'Error interno del servidor.' });
    }
};

usersCtrl.signin = (req, res, next) => {
    passport.authenticate('local', async (err, user, info) => {
        if (err) {
            return res.status(500).json({ message: 'Error interno del servidor.' });
        }
        if (!user) {
            return res.status(400).json({ message: info.message });
        }
        try {
            const isMatch = await user.matchPassword(req.body.password);
            if (!isMatch) {
                return res.status(400).json({ message: 'Contraseña incorrecta.' });
            }

            const userData = {
                id: user.id,
                name: user.name,
                email: user.email,
            };
            return res.status(200).send(JSON.stringify(userData));
        } catch (error) {
            console.error('Error al comparar contraseñas:', error);
            return res.status(500).json({ message: 'Error interno del servidor.' });
        }
    })(req, res, next);
};

module.exports = usersCtrl;
