const profileCtrl = {};
const User = require('../models/users');
const passport = require('passport');
require('dotenv').config();

const user = require('../models/users'); // Asegúrate de importar el modelo User correctamente

profileCtrl.profile = async (req, res) => {
    const { id } = req.body;

    try {
        const user = await User.findOne({ _id: id });

        if (!user) {
            return res.status(404).json({ message: 'Usuario no encontrado.' });
        }

        return res.status(200).json({
            id: user.id,
            name: user.name,
            email: user.email,
            password: user.password,
            direction: user.direction,
            phone: user.phone,
        });
    } catch (error) {
        console.error('Error al buscar usuario por ID:', error);
        return res.status(500).json({ message: 'Error interno del servidor.' });
    }
};

profileCtrl.updateProfile = async (req, res) => {
    const { id, name, email, password, direction, phone } = req.body;

    try {
        // Busca el usuario por su ID
        const user = await User.findOne({ _id: id });

        if (!user) {
            return res.status(404).json({ message: 'Usuario no encontrado.' });
        }

        // Actualiza los campos del usuario si se proporcionan y no están vacíos
        if (name) {
            user.name = name;
        }
        if (email) {
            user.email = email;
        }
        if (password !== '') {
            user.password = await user.encryptPassword(password); // Encripta la contraseña antes de actualizarla
        }
        if (direction) {
            user.direction = direction;
        }
        if (phone) {
            user.phone = phone;
        }

        // Guarda los cambios en la base de datos
        await user.save();

        return res.status(200).json({ message: 'Perfil actualizado exitosamente.' });
    } catch (error) {
        console.error('Error al actualizar perfil:', error);
        return res.status(500).json({ message: 'Error interno del servidor.' });
    }
};

module.exports = profileCtrl;
