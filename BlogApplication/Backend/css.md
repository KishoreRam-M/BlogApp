@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css');

:root {
  --primary-color: #4f46e5;
  --secondary-color: #8b5cf6;
  --accent-color: #3b82f6;
  --text-color: #1f2937;
  --text-light: #6b7280;
  --background-light: #f9fafb;
  --background-dark: #111827;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Poppins', sans-serif;
  background-color: var(--background-light);
  color: var(--text-color);
  line-height: 1.6;
}

/* Button Styles */
button, 
.button {
  cursor: pointer;
  transition: all 0.2s ease;
}

button:hover, 
.button:hover {
  transform: translateY(-2px);
}

button:active, 
.button:active {
  transform: translateY(0);
}

/* Header Animation */
header {
  position: relative;
  overflow: hidden;
}

header::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, rgba(255,255,255,0) 70%);
  animation: pulse 15s ease-in-out infinite;
  z-index: 0;
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 0.3;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.1;
  }
  100% {
    transform: scale(1);
    opacity: 0.3;
  }
}

header > * {
  position: relative;
  z-index: 1;
}

/* Card Hover Effects */
.post-card {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.post-card:hover {
  transform: translateY(-5px);
}

/* Smooth ScrollTo */
html {
  scroll-behavior: smooth;
}

/* Responsive Typography */
h1 {
  font-size: clamp(1.875rem, 5vw, 2.5rem);
}

h2 {
  font-size: clamp(1.5rem, 4vw, 2rem);
}

p {
  font-size: clamp(0.875rem, 2vw, 1rem);
}

/* Loading Animation */
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.loading-spinner {
  border-radius: 50%;
  width: 48px;
  height: 48px;
  border: 4px solid rgba(79, 70, 229, 0.1);
  border-top-color: var(--primary-color);
  animation: spin 1s linear infinite;
}

/* Custom Scrollbar */
::-webkit-scrollbar {
  width: 10px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 5px;
}

::-webkit-scrollbar-thumb:hover {
  background: #555;
}

/* Form Focus Effects */
input:focus,
textarea:focus {
  outline: none;
  box-shadow: 0 0 0 2px rgba(79, 70, 229, 0.3);
  border-color: var(--primary-color) !important;
}

/* Card Animation */
.card-enter {
  opacity: 0;
  transform: translateY(20px);
}

.card-enter-active {
  opacity: 1;
  transform: translateY(0);
  transition: opacity 300ms, transform 300ms;
}

/* Like Button Animation */
@keyframes likeAnimation {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.3);
  }
  100% {
    transform: scale(1);
  }
}

.like-animation {
  animation: likeAnimation 0.3s ease;
}

/* Custom styling for article content */
.prose p {
  margin-bottom: 1.5rem;
}

.prose h2 {
  font-size: 1.5rem;
  font-weight: 600;
  margin-top: 2rem;
  margin-bottom: 1rem;
  color: var(--text-color);
}

.prose h3 {
  font-size: 1.25rem;
  font-weight: 600;
  margin-top: 1.5rem;
  margin-bottom: 0.75rem;
  color: var(--text-color);
}

.prose ul, .prose ol {
  margin-left: 1.5rem;
  margin-bottom: 1.5rem;
}

.prose li {
  margin-bottom: 0.5rem;
}

.prose blockquote {
  border-left: 4px solid var(--primary-color);
  padding-left: 1rem;
  margin-left: 0;
  margin-right: 0;
  font-style: italic;
  color: var(--text-light);
}

.prose code {
  background-color: #f3f4f6;
  padding: 0.2rem 0.4rem;
  border-radius: 0.25rem;
  font-family: monospace;
  font-size: 0.9em;
}

.prose pre {
  background-color: #1f2937;
  color: #f9fafb;
  padding: 1rem;
  border-radius: 0.5rem;
  overflow-x: auto;
  margin-bottom: 1.5rem;
}

.prose pre code {
  background-color: transparent;
  color: inherit;
  padding: 0;
}

/* Mobile menu */
@media (max-width: 768px) {
  .mobile-menu {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: var(--background-dark);
    z-index: 1000;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    transition: transform 0.3s ease;
  }
  
  .mobile-menu.hidden {
    transform: translateY(-100%);
  }
  
  .mobile-menu a {
    color: white;
    font-size: 1.25rem;
    margin: 1rem 0;
  }
}

/* Comment hover effect */
.comment:hover {
  background-color: #f9fafb;
}

/* Text gradient */
.text-gradient {
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  background-image: linear-gradient(to right, var(--primary-color), var(--secondary-color));
}

/* Fade in animation */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.fade-in {
  animation: fadeIn 0.5s ease-in;
}

/* Accessibility focus styles */
a:focus, button:focus, input:focus, textarea:focus, select:focus {
  outline: 2px solid var(--primary-color);
  outline-offset: 2px;
}

/* Utility classes to complement Tailwind */
.transition-300 {
  transition: all 0.3s ease;
}

.hover-lift {
  transition: transform 0.3s ease;
}

.hover-lift:hover {
  transform: translateY(-3px);
}
