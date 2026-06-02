from flask import Flask, request, jsonify
import numpy as np
from sklearn.ensemble import RandomForestClassifier

app = Flask(__name__)

def train_ai_model():
    X_train = np.array([
        [15, 0, 1, 0],  
        [85, 1, 4, 1],  
        [22, 0, 2, 0], 
        [90, 0, 3, 1], 
        [35, 0, 2, 0], 
        [110, 1, 5, 1] 
    ])
    y_train = np.array([0, 1, 0, 1, 0, 1]) 

    model = RandomForestClassifier(n_estimators=100, random_state=42)
    model.fit(X_train, y_train)
    return model

ai_model = train_ai_model()

def extract_features(url):
    url_len = len(url)
    has_at = 1 if '@' in url else 0
    dot_count = url.count('.')
    no_https = 1 if not url.lower().startswith('https://') else 0
    
    return np.array([[url_len, has_at, dot_count, no_https]])

@app.route('/scan', methods=['POST'])
def scan_url():
    data = request.get_json()
    if not data or 'url' not in data:
        return jsonify({'status': 'Error', 'message': 'Invalid Input'}), 400
    
    target_url = data['url']
    
    features = extract_features(target_url)
    prediction = ai_model.predict(features)[0]
    
    probabilities = ai_model.predict_proba(features)[0]
    confidence = round(probabilities[prediction] * 100, 2)
    
    result_status = "PHISHING" if prediction == 1 else "SAFE"
    
    return jsonify({
        'url': target_url,
        'status': result_status,
        'confidence': f"{confidence}%"
    })

if __name__ == '__main__':
    print("Server is running on port 5000...")
    app.run(port=5000, debug=True)