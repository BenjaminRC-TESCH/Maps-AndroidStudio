from flask import Flask, request, jsonify, render_template
from flask_cors import CORS
import pandas as pd

app = Flask(__name__)
CORS(app)


def cargar_datos(ruta_csv):
    data = pd.read_csv(ruta_csv)
    return data


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/categorias', methods=['GET'])
def categorias():
    categorias = data['Categoria'].unique().tolist()
    return jsonify(categorias)


@app.route('/preguntas/<categoria>', methods=['GET'])
def preguntas(categoria):
    preguntas = data[data['Categoria'] == categoria]['Pregunta'].tolist()
    return jsonify(preguntas)


@app.route('/respuesta/<pregunta>', methods=['GET'])
def respuesta(pregunta):
    try:
        respuesta = data[data['Pregunta'] == pregunta]['Respuesta'].values[0]
    except IndexError:
        respuesta = "Lo siento, no tengo una respuesta para esa pregunta en este momento."
    return jsonify(respuesta)


if __name__ == "__main__":
    ruta_csv = 'entrena.csv'
    data = cargar_datos(ruta_csv)
    app.run(host="0.0.0.0", port=5000, debug=True)
