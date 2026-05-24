# Deployment Guide

This guide will help you deploy the Course Management Platform to production.

## Quick Start Checklist

- [ ] Backend deployed and running
- [ ] Database created and accessible
- [ ] Frontend deployed
- [ ] Environment variables configured
- [ ] CORS configured properly
- [ ] Demo data seeded
- [ ] Health check passing

## Backend Deployment

### Option 1: Railway (Recommended for Spring Boot)

1. **Create Railway Account**
   - Go to [railway.app](https://railway.app)
   - Sign up with GitHub

2. **Create MySQL Database**
   - Click "New Project" → "Provision MySQL"
   - Note the connection details

3. **Deploy Backend**
   - Click "New" → "GitHub Repo"
   - Select your repository
   - Set root directory to `backend`
   - Add environment variables:
     ```
     SPRING_DATASOURCE_URL=jdbc:mysql://[host]:[port]/railway
     SPRING_DATASOURCE_USERNAME=root
     SPRING_DATASOURCE_PASSWORD=[password]
     JWT_SECRET=your-super-secret-jwt-key-change-this-in-production
     CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com
     ```

4. **Get Deployment URL**
   - Railway will provide a URL like: `https://your-app.up.railway.app`

### Option 2: Render

1. **Create Render Account**
   - Go to [render.com](https://render.com)

2. **Create MySQL Database**
   - Click "New" → "PostgreSQL" (or use external MySQL)

3. **Create Web Service**
   - Click "New" → "Web Service"
   - Connect your GitHub repository
   - Settings:
     - **Root Directory**: `backend`
     - **Build Command**: `mvn clean package`
     - **Start Command**: `java -jar target/course-platform-1.0.0.jar`
   - Add environment variables (same as Railway)

### Option 3: Heroku

1. **Install Heroku CLI**
```bash
npm install -g heroku
```

2. **Login and Create App**
```bash
heroku login
heroku create your-app-name
```

3. **Add MySQL Database**
```bash
heroku addons:create cleardb:ignite
```

4. **Deploy**
```bash
cd backend
git init
heroku git:remote -a your-app-name
git add .
git commit -m "Deploy backend"
git push heroku main
```

## Frontend Deployment

### Option 1: Vercel (Recommended)

1. **Install Vercel CLI**
```bash
npm install -g vercel
```

2. **Deploy**
```bash
cd frontend
vercel
```

3. **Configure Environment**
   - Go to Vercel dashboard
   - Settings → Environment Variables
   - Add: `REACT_APP_API_URL=https://your-backend-url.com/api`

4. **Redeploy**
```bash
vercel --prod
```

### Option 2: Netlify

1. **Install Netlify CLI**
```bash
npm install -g netlify-cli
```

2. **Build and Deploy**
```bash
cd frontend
npm run build
netlify deploy --prod --dir=build
```

3. **Configure Environment**
   - Go to Netlify dashboard
   - Site settings → Environment variables
   - Add: `REACT_APP_API_URL=https://your-backend-url.com/api`

### Option 3: GitHub Pages

1. **Update package.json**
```json
{
  "homepage": "https://yourusername.github.io/repo-name",
  "scripts": {
    "predeploy": "npm run build",
    "deploy": "gh-pages -d build"
  }
}
```

2. **Install gh-pages**
```bash
npm install --save-dev gh-pages
```

3. **Deploy**
```bash
npm run deploy
```

## Database Setup

### Production Database Configuration

1. **Create Production Database**
```sql
CREATE DATABASE course_management_db;
```

2. **Update application.properties** for production:
```properties
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
```

3. **Seed Demo Data**
   - The `data.sql` file will automatically run on startup
   - Includes 1 admin, 1 student, and 3 sample courses

## Environment Variables

### Backend (.env or platform config)
```
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://host:port/database
SPRING_DATASOURCE_USERNAME=username
SPRING_DATASOURCE_PASSWORD=password

# JWT
JWT_SECRET=your-very-long-and-secure-secret-key-here
JWT_EXPIRATION=86400000

# CORS
CORS_ALLOWED_ORIGINS=https://your-frontend.com,https://www.your-frontend.com

# Server
SERVER_PORT=8080
```

### Frontend (.env or platform config)
```
REACT_APP_API_URL=https://your-backend-api.com/api
```

## Post-Deployment Checklist

1. **Test Authentication**
   - [ ] Login with admin account
   - [ ] Login with student account
   - [ ] Register new user

2. **Test Admin Features**
   - [ ] Create course
   - [ ] Edit course
   - [ ] Delete course

3. **Test Student Features**
   - [ ] Browse courses
   - [ ] View course details
   - [ ] Enroll in course
   - [ ] Update progress

4. **Test Search & Filter**
   - [ ] Search by title
   - [ ] Filter by category
   - [ ] Combined search and filter

5. **Test Mobile Responsiveness**
   - [ ] Test on mobile device
   - [ ] Check all pages are responsive

## Troubleshooting

### CORS Errors

**Problem**: Frontend can't connect to backend

**Solution**: Add frontend URL to CORS configuration:
```properties
cors.allowed.origins=https://your-frontend.com
```

### Database Connection Failed

**Problem**: Backend can't connect to database

**Solution**: 
- Check DATABASE_URL is correct
- Verify database is running
- Check firewall rules

### 404 on Frontend Routes

**Problem**: Refreshing page gives 404

**Solution** (Vercel): Create `vercel.json`:
```json
{
  "rewrites": [
    { "source": "/(.*)", "destination": "/index.html" }
  ]
}
```

**Solution** (Netlify): Create `public/_redirects`:
```
/*    /index.html   200
```

### JWT Token Issues

**Problem**: Authentication fails

**Solution**:
- Check JWT_SECRET is set
- Verify token expiration time
- Check token in localStorage

## Security Checklist

- [ ] Change default JWT secret
- [ ] Use strong database passwords
- [ ] Enable HTTPS (most platforms do this automatically)
- [ ] Set secure CORS origins (don't use *)
- [ ] Review and update application.properties for production
- [ ] Don't commit .env files to Git
- [ ] Use environment variables for all secrets

## Monitoring

### Health Check Endpoint

Add to Spring Boot (optional):
```java
@RestController
public class HealthController {
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
```

### Logging

Check application logs:
- Railway: Dashboard → Deployments → Logs
- Render: Dashboard → Logs
- Vercel: Dashboard → Deployments → Function Logs

## Scaling Considerations

1. **Database**: Use connection pooling
2. **Backend**: Enable horizontal scaling if needed
3. **Frontend**: Use CDN (Vercel/Netlify do this automatically)
4. **Caching**: Consider Redis for session management

## Cost Estimates

### Free Tier Options
- **Railway**: Free tier with 500 hours/month
- **Render**: Free tier available
- **Vercel**: Free for personal projects
- **Netlify**: Free for personal projects

### Production Costs
- **Database**: $5-20/month for managed MySQL
- **Backend**: $7-25/month for container hosting
- **Frontend**: Usually free with Vercel/Netlify
- **Total**: ~$10-50/month depending on traffic

## Support

If you encounter issues:
1. Check logs in deployment platform
2. Verify environment variables
3. Test API endpoints with Postman
4. Check CORS configuration
5. Review database connection

## Next Steps

After deployment:
1. Share demo credentials with evaluators
2. Test all features in production
3. Monitor logs for errors
4. Collect feedback
5. Plan future enhancements
