import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './App.css';

// API base URL
const API_BASE_URL = 'http://localhost:8080/api';

// Header Component
const Header = ({ searchTerm, setSearchTerm, handleSearch }) => {
  return (
    <header className="bg-gradient-to-r from-purple-600 to-blue-500 text-white p-4 shadow-lg">
      <div className="container mx-auto flex flex-col md:flex-row justify-between items-center">
        <Link to="/" className="text-2xl font-bold mb-4 md:mb-0">Thoughtscape</Link>
        <div className="flex w-full md:w-auto">
          <input
            type="text"
            placeholder="Search posts..."
            className="p-2 rounded-l text-gray-800 w-full md:w-64"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <button 
            onClick={handleSearch}
            className="bg-blue-700 hover:bg-blue-800 px-4 py-2 rounded-r transition-colors"
          >
            Search
          </button>
        </div>
        <nav className="hidden md:flex space-x-4 mt-4 md:mt-0">
          <Link to="/" className="hover:text-blue-200 transition-colors">Home</Link>
          <Link to="/new-post" className="hover:text-blue-200 transition-colors">New Post</Link>
        </nav>
      </div>
    </header>
  );
};

// Footer Component
const Footer = () => {
  return (
    <footer className="bg-gray-800 text-white p-6 mt-12">
      <div className="container mx-auto">
        <div className="flex flex-col md:flex-row justify-between items-center">
          <div className="mb-4 md:mb-0">
            <h3 className="text-xl font-bold">Thoughtscape</h3>
            <p className="text-gray-400">Share your thoughts with the world</p>
          </div>
          <div className="flex space-x-4">
            <a href="#" className="text-gray-400 hover:text-white transition-colors">
              <i className="fab fa-twitter text-xl"></i>
            </a>
            <a href="#" className="text-gray-400 hover:text-white transition-colors">
              <i className="fab fa-facebook text-xl"></i>
            </a>
            <a href="#" className="text-gray-400 hover:text-white transition-colors">
              <i className="fab fa-instagram text-xl"></i>
            </a>
          </div>
        </div>
        <div className="mt-6 text-center text-gray-400 text-sm">
          &copy; {new Date().getFullYear()} Thoughtscape. All rights reserved.
        </div>
      </div>
    </footer>
  );
};

// Post Card Component
const PostCard = ({ post }) => {
  const navigate = useNavigate();
  
  const truncateContent = (content, maxLength = 150) => {
    if (!content) return '';
    return content.length > maxLength ? content.substring(0, maxLength) + '...' : content;
  };
  
  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };
  
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-xl transition-shadow duration-300 flex flex-col">
      <div className="p-6 flex-grow">
        <h2 className="text-xl font-bold mb-2 text-gray-800">{post.name}</h2>
        <p className="text-gray-600 mb-4">{formatDate(post.date)}</p>
        <p className="text-gray-700">{truncateContent(post.content)}</p>
      </div>
      <div className="px-6 py-4 bg-gray-50 flex justify-between items-center">
        <div className="flex items-center text-gray-700">
          <span className="mr-2">
            <i className="far fa-heart"></i>
          </span>
          <span>{post.likeCount}</span>
          <span className="ml-4 mr-2">
            <i className="far fa-eye"></i>
          </span>
          <span>{post.viewCount}</span>
        </div>
        <button 
          onClick={() => navigate(`/post/${post.id}`)}
          className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded text-sm transition-colors"
        >
          Read More
        </button>
      </div>
    </div>
  );
};

// Home Page Component
const HomePage = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [isSearching, setIsSearching] = useState(false);
  
  useEffect(() => {
    fetchPosts();
  }, []);
  
  const fetchPosts = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_BASE_URL}/posts`);
      setPosts(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching posts:', error);
      setLoading(false);
    }
  };
  
  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      setIsSearching(false);
      return;
    }
    
    try {
      setLoading(true);
      setIsSearching(true);
      const response = await axios.get(`${API_BASE_URL}/posts/search/${searchTerm}`);
      setSearchResults(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error searching posts:', error);
      setLoading(false);
    }
  };
  
  const displayPosts = isSearching ? searchResults : posts;
  
  return (
    <div className="min-h-screen flex flex-col">
      <Header 
        searchTerm={searchTerm} 
        setSearchTerm={setSearchTerm} 
        handleSearch={handleSearch} 
      />
      
      <main className="container mx-auto px-4 py-8 flex-grow">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">
            {isSearching ? `Search Results for "${searchTerm}"` : "Latest Posts"}
          </h1>
          {isSearching && (
            <button 
              onClick={() => {
                setIsSearching(false);
                setSearchTerm('');
              }}
              className="text-blue-500 hover:text-blue-700 transition-colors"
            >
              Back to all posts
            </button>
          )}
        </div>
        
        {loading ? (
          <div className="flex justify-center items-center h-64">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
          </div>
        ) : displayPosts.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {displayPosts.map(post => (
              <PostCard key={post.id} post={post} />
            ))}
          </div>
        ) : (
          <div className="text-center py-12">
            <h2 className="text-2xl font-semibold text-gray-600">
              {isSearching ? 'No posts found matching your search.' : 'No posts available.'}
            </h2>
            <p className="mt-2 text-gray-500">
              {isSearching ? 'Try a different search term.' : 'Be the first to create a post!'}
            </p>
          </div>
        )}
      </main>
      
      <Footer />
    </div>
  );
};

// Post Detail Page Component
const PostDetailPage = () => {
  const { id } = useParams();
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [commentAuthor, setCommentAuthor] = useState('');
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();
  
  useEffect(() => {
    fetchPostDetails();
    fetchComments();
  }, [id]);
  
  const fetchPostDetails = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_BASE_URL}/posts/${id}`);
      setPost(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching post details:', error);
      setLoading(false);
    }
  };
  
  const fetchComments = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/comments/post/${id}`);
      setComments(response.data);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };
  
  const handleLike = async () => {
    try {
      const response = await axios.put(`${API_BASE_URL}/posts/${id}/like`);
      setPost(response.data);
    } catch (error) {
      console.error('Error liking post:', error);
    }
  };
  
  const handleSubmitComment = async (e) => {
    e.preventDefault();
    
    if (!newComment.trim() || !commentAuthor.trim()) return;
    
    try {
      await axios.post(`${API_BASE_URL}/comments/create`, null, {
        params: {
          postId: id,
          postBy: commentAuthor,
          content: newComment
        }
      });
      
      setNewComment('');
      fetchComments();
    } catch (error) {
      console.error('Error submitting comment:', error);
    }
  };
  
  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };
  
  const handleSearch = () => {
    navigate('/');
  };
  
  if (loading) {
    return (
      <div className="min-h-screen">
        <Header searchTerm={searchTerm} setSearchTerm={setSearchTerm} handleSearch={handleSearch} />
        <div className="container mx-auto px-4 py-16 flex justify-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
        </div>
        <Footer />
      </div>
    );
  }
  
  if (!post) {
    return (
      <div className="min-h-screen">
        <Header searchTerm={searchTerm} setSearchTerm={setSearchTerm} handleSearch={handleSearch} />
        <div className="container mx-auto px-4 py-16 text-center">
          <h1 className="text-2xl font-semibold text-gray-700">Post not found</h1>
          <button 
            onClick={() => navigate('/')}
            className="mt-6 bg-blue-500 hover:bg-blue-600 text-white px-6 py-2 rounded transition-colors"
          >
            Back to Home
          </button>
        </div>
        <Footer />
      </div>
    );
  }
  
  return (
    <div className="min-h-screen flex flex-col">
      <Header searchTerm={searchTerm} setSearchTerm={setSearchTerm} handleSearch={handleSearch} />
      
      <main className="container mx-auto px-4 py-8 flex-grow">
        <div className="mb-6">
          <button 
            onClick={() => navigate('/')}
            className="text-blue-500 hover:text-blue-700 flex items-center transition-colors"
          >
            <span className="mr-2">←</span> Back to all posts
          </button>
        </div>
        
        <article className="bg-white rounded-lg shadow-lg overflow-hidden">
          <div className="p-8">
            <h1 className="text-3xl font-bold mb-4 text-gray-800">{post.name}</h1>
            <p className="text-gray-600 mb-6">{formatDate(post.date)}</p>
            
            <div className="prose max-w-none text-gray-700 mb-8">
              {post.content.split('\n').map((paragraph, index) => (
                <p key={index} className="mb-4">{paragraph}</p>
              ))}
            </div>
            
            <div className="flex items-center text-gray-700">
              <button 
                onClick={handleLike}
                className="flex items-center text-gray-700 hover:text-red-500 transition-colors"
              >
                <span className="mr-2">
                  <i className="far fa-heart"></i>
                </span>
                <span>{post.likeCount}</span>
              </button>
              <span className="ml-4 mr-2">
                <i className="far fa-eye"></i>
              </span>
              <span>{post.viewCount}</span>
            </div>
          </div>
        </article>
        
        <section className="mt-12">
          <h2 className="text-2xl font-bold mb-6 text-gray-800">Comments</h2>
          
          <form onSubmit={handleSubmitComment} className="bg-white p-6 rounded-lg shadow-md mb-8">
            <div className="mb-4">
              <label htmlFor="commentAuthor" className="block text-gray-700 mb-2">Your Name</label>
              <input
                type="text"
                id="commentAuthor"
                className="w-full p-2 border border-gray-300 rounded"
                value={commentAuthor}
                onChange={(e) => setCommentAuthor(e.target.value)}
                required
              />
            </div>
            <div className="mb-4">
              <label htmlFor="commentContent" className="block text-gray-700 mb-2">Comment</label>
              <textarea
                id="commentContent"
                rows="4"
                className="w-full p-2 border border-gray-300 rounded"
                value={newComment}
                onChange={(e) => setNewComment(e.target.value)}
                required
              ></textarea>
            </div>
            <button 
              type="submit"
              className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded transition-colors"
            >
              Post Comment
            </button>
          </form>
          
          {comments.length > 0 ? (
            <div className="space-y-6">
              {comments.map(comment => (
                <div key={comment.id} className="bg-white p-6 rounded-lg shadow-md">
                  <div className="flex justify-between items-center mb-4">
                    <h3 className="font-semibold text-gray-800">{comment.postBy}</h3>
                    <span className="text-gray-500 text-sm">{formatDate(comment.createdAt)}</span>
                  </div>
                  <p className="text-gray-700">{comment.content}</p>
                </div>
              ))}
            </div>
          ) : (
            <div className="bg-white p-6 rounded-lg shadow-md text-center text-gray-500">
              Be the first to comment on this post!
            </div>
          )}
        </section>
      </main>
      
      <Footer />
    </div>
  );
};

// Create New Post Page
const NewPostPage = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!title.trim() || !content.trim()) {
      setError('Please fill in all fields.');
      return;
    }
    
    try {
      setLoading(true);
      setError('');
      
      const newPost = {
        name: title,
        content
      };
      
      await axios.post(`${API_BASE_URL}/posts`, newPost);
      navigate('/');
    } catch (error) {
      console.error('Error creating post:', error);
      setError('Failed to create post. Please try again.');
      setLoading(false);
    }
  };
  
  const handleSearch = () => {
    navigate('/');
  };
  
  return (
    <div className="min-h-screen flex flex-col">
      <Header searchTerm={searchTerm} setSearchTerm={setSearchTerm} handleSearch={handleSearch} />
      
      <main className="container mx-auto px-4 py-8 flex-grow">
        <div className="mb-6">
          <button 
            onClick={() => navigate('/')}
            className="text-blue-500 hover:text-blue-700 flex items-center transition-colors"
          >
            <span className="mr-2">←</span> Back to all posts
          </button>
        </div>
        
        <div className="bg-white rounded-lg shadow-lg p-8">
          <h1 className="text-3xl font-bold mb-6 text-gray-800">Create New Post</h1>
          
          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
              {error}
            </div>
          )}
          
          <form onSubmit={handleSubmit}>
            <div className="mb-6">
              <label htmlFor="title" className="block text-gray-700 font-semibold mb-2">Title</label>
              <input
                type="text"
                id="title"
                className="w-full p-3 border border-gray-300 rounded"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>
            
            <div className="mb-6">
              <label htmlFor="content" className="block text-gray-700 font-semibold mb-2">Content</label>
              <textarea
                id="content"
                rows="12"
                className="w-full p-3 border border-gray-300 rounded"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                required
              ></textarea>
            </div>
            
            <button 
              type="submit"
              className="bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded font-semibold transition-colors"
              disabled={loading}
            >
              {loading ? 'Posting...' : 'Publish Post'}
            </button>
          </form>
        </div>
      </main>
      
      <Footer />
    </div>
  );
};

// Main App Component
function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-100">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/post/:id" element={<PostDetailPage />} />
          <Route path="/new-post" element={<NewPostPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
