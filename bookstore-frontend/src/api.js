import axios from 'axios';

const base64Encode = (username, password) => {
    const credentials = `${username}:${password}`;
    return btoa(credentials);
  };
  
  const encodedCredentials = base64Encode('admin', 'adminpassword');
  

export default axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Authorization': `Basic ${encodedCredentials}`,
  },
});